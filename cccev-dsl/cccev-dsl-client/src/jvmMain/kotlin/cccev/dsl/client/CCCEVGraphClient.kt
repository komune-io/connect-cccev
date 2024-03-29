package cccev.dsl.client

import cccev.dsl.client.graph.InformationConceptGraphInitializer
import cccev.dsl.client.graph.RequirementGraphInitializer
import cccev.dsl.model.DataUnitDTO
import cccev.dsl.model.EvidenceTypeBase
import cccev.dsl.model.EvidenceTypeListBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptRef
import cccev.dsl.model.ReferenceFramework
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementRef
import cccev.f2.concept.client.InformationConceptClient
import cccev.f2.concept.domain.query.InformationConceptGetByIdentifierQueryDTOBase
import cccev.f2.evidence.type.client.EvidenceTypeClient
import cccev.f2.evidence.type.domain.query.EvidenceTypeGetByIdentifierQueryDTOBase
import cccev.f2.evidence.type.domain.query.EvidenceTypeListGetByIdentifierQueryDTOBase
import cccev.f2.framework.client.FrameworkClient
import cccev.f2.framework.domain.query.FrameworkGetByIdentifierQueryDTOBase
import cccev.f2.requirement.domain.command.RequirementCreateCommandDTOBase
import cccev.f2.requirement.domain.model.RequirementDTOBase
import cccev.f2.requirement.domain.query.RequirementGetByIdentifierQueryDTOBase
import cccev.f2.unit.client.DataUnitClient
import cccev.f2.unit.domain.command.DataUnitCreateCommandDTOBase
import cccev.f2.unit.domain.command.DataUnitOptionCreateCommandDTOBase
import cccev.f2.unit.domain.command.DataUnitOptionUpdateCommandDTOBase
import cccev.f2.unit.domain.command.DataUnitUpdateCommandDTOBase
import cccev.f2.unit.domain.query.DataUnitGetByIdentifierQueryDTOBase
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.s2.concept.domain.command.InformationConceptUpdateCommand
import cccev.s2.evidence.type.domain.EvidenceTypeId
import cccev.s2.evidence.type.domain.EvidenceTypeListId
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.type.domain.command.type.EvidenceTypeCreateCommand
import cccev.s2.framework.domain.FrameworkId
import cccev.s2.framework.domain.command.FrameworkCreateCommand
import cccev.s2.requirement.client.RequirementClient
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.command.RequirementAddRequirementsCommand
import cccev.s2.requirement.domain.command.RequirementUpdateCommand
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.unit.domain.DataUnitId
import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

class CCCEVGraphClient(
    private val evidenceTypeClient: EvidenceTypeClient,
    private val informationConceptClient: InformationConceptClient,
    private val requirementClient: RequirementClient,
    private val dataUnitClient: DataUnitClient,
    private val frameworkClient: FrameworkClient,
) {
    private val informationConceptGraphInitializer = InformationConceptGraphInitializer(informationConceptClient)
    private val requirementGraphInitializer = RequirementGraphInitializer(requirementClient)

    suspend fun save(graph: Flow<Requirement>): Flow<RequirementDTOBase> {
        val context = Context()
        val requirements = graph.flatCollect()

        val requirementNodes = requirements.groupBy(Requirement::identifier)
            .mapValues { (_, requirements) -> requirements.firstOrNull { it !is RequirementRef } ?: requirements.first() }

        val informationConceptNodes = requirements.flatMap { requirement -> requirement.hasConcept.orEmpty() }
            .groupBy(InformationConcept::identifier)
            .mapValues { (_, concepts) -> concepts.firstOrNull { it !is InformationConceptRef } ?: concepts.first() }

        informationConceptGraphInitializer.dryRun(informationConceptNodes.values) { it.identifier }
        requirementGraphInitializer.dryRun(requirementNodes.values) { it.identifier }

        informationConceptNodes.values.save(context)
        requirementNodes.values.save(context)

        return context.resultRequirements.asFlow()
    }

    @Deprecated("Use save instead", ReplaceWith("save"))
    suspend fun create(requirements: Flow<Requirement>): Flow<RequirementDTOBase> {
        return save(requirements)
    }

    @OptIn(FlowPreview::class)
    private suspend fun Flow<Requirement>.flatCollect(): List<Requirement> {
        val visitedRequirementIdentifiers = mutableSetOf<RequirementIdentifier>()

        fun Requirement.flatten(): Flow<Requirement> = flow {
            if (identifier in visitedRequirementIdentifiers) {
                return@flow
            }

            hasRequirement?.forEach { emitAll(it.flatten()) }
            isRequirementOf?.forEach { emitAll(it.flatten()) }
            hasQualifiedRelation?.values?.flatten()?.forEach { emitAll(it.flatten()) }
            emit(this@flatten)
        }

        return flatMapConcat(Requirement::flatten).toList()
    }

    @JvmName("saveInformationConcepts")
    private suspend fun Collection<InformationConcept>.save(context: Context) {
        val dataUnits = mapNotNull(InformationConcept::unit)
        context.processedUnits.putAll(dataUnits.associate { it.identifier to it.save() })

        val processedConcepts = informationConceptGraphInitializer.initialize(this) { concept, dependencies ->
            context.processedConcepts.putAllNew(dependencies)
            concept.save(context)
        }
        context.processedConcepts.putAllNew(processedConcepts)
    }

    @JvmName("saveRequirements")
    private suspend fun Collection<Requirement>.save(context: Context) {
        val processedRequirements = requirementGraphInitializer.initialize(this) { requirement, dependencies ->
            context.processedRequirements.putAllNew(dependencies)

            requirement.isDerivedFrom?.forEach { framework ->
                if (framework.identifier !in context.processedFrameworks) {
                    val frameworkId = framework.getOrCreate()
                    context.processedFrameworks[framework.identifier] = frameworkId
                }
            }

            requirement.hasEvidenceTypeList?.forEach { etl ->
                initEvidenceTypeList(etl, context)
            }

            requirement.save(context)
                .also { context.resultRequirements.add(it) }
                .id
        }
        context.processedRequirements.putAllNew(processedRequirements)
    }

    private suspend fun Requirement.save(context: Context): RequirementDTOBase {
        val existingRequirement = RequirementGetByIdentifierQueryDTOBase(identifier)
            .invokeWith(requirementClient.requirementGetByIdentifier())
            .item

        if (this is RequirementRef) {
            return existingRequirement
                ?: throw IllegalArgumentException("Requirement [$identifier] does not exist, cannot reference it")
        }

        val requirementId = if (existingRequirement == null) {
            createRequirement(this, context)
        } else {
            updateRequirement(this, existingRequirement.id, context)
        }
        context.processedRequirements[this.identifier] = requirementId

        this.isRequirementOf?.forEach { parent ->
            RequirementAddRequirementsCommand(
                id = context.processedRequirements[parent.identifier]!!,
                requirementIds = listOf(requirementId)
            ).invokeWith(requirementClient.requirementAddRequirements())
        }
        return RequirementGetByIdentifierQueryDTOBase(
            identifier = this.identifier
        ).invokeWith(requirementClient.requirementGetByIdentifier()).item!!
    }

    private suspend fun createRequirement(
        requirement: Requirement,
        context: Context
    ): RequirementId {
        return RequirementCreateCommandDTOBase(
            identifier = requirement.identifier,
            name = requirement.name,
            description = requirement.description,
            isDerivedFrom = requirement.isDerivedFrom?.map { context.processedFrameworks[it.identifier]!! }.orEmpty(),
            hasConcept = requirement.hasConcept?.map { context.processedConcepts[it.identifier]!! }.orEmpty(),
            hasEvidenceTypeList = requirement.hasEvidenceTypeList?.map { context.processedEvidenceTypeLists[it.identifier]!! }
                .orEmpty(),
            hasRequirement = requirement.hasRequirement?.map { context.processedRequirements[it.identifier]!! }.orEmpty(),
            hasQualifiedRelation = requirement.hasQualifiedRelation?.mapValues { (_, requirements) ->
                requirements.map { context.processedRequirements[it.identifier]!! }
            }.orEmpty(),
            kind = requirement.kind,
            type = requirement.type?.toString(),
            enablingCondition = requirement.enablingCondition,
            enablingConditionDependencies = requirement.enablingConditionDependencies.map { context.processedConcepts[it]!! },
            required = requirement.required,
            validatingCondition = requirement.validatingCondition,
            validatingConditionDependencies = requirement.validatingConditionDependencies.map { context.processedConcepts[it]!! },
            order = requirement.order,
            properties = requirement.properties
        ).invokeWith(requirementClient.requirementCreate()).id
    }

    private suspend fun updateRequirement(
        requirement: Requirement,
        id: RequirementId,
        context: Context
    ): RequirementId {
        return RequirementUpdateCommand(
            id = id,
            name = requirement.name,
            description = requirement.description,
            hasConcept = requirement.hasConcept?.map { context.processedConcepts[it.identifier]!! }.orEmpty(),
            hasEvidenceTypeList = requirement.hasEvidenceTypeList?.map { context.processedEvidenceTypeLists[it.identifier]!! }
                .orEmpty(),
            hasRequirement = requirement.hasRequirement?.map { context.processedRequirements[it.identifier]!! }.orEmpty(),
            hasQualifiedRelation = requirement.hasQualifiedRelation?.mapValues { (_, requirements) ->
                requirements.map { context.processedRequirements[it.identifier]!! }
            }.orEmpty(),
            type = requirement.type?.toString(),
            enablingCondition = requirement.enablingCondition,
            enablingConditionDependencies = requirement.enablingConditionDependencies.map { context.processedConcepts[it]!! },
            required = requirement.required,
            validatingCondition = requirement.validatingCondition,
            validatingConditionDependencies = requirement.validatingConditionDependencies.map { context.processedConcepts[it]!! },
            order = requirement.order,
            properties = requirement.properties
        ).invokeWith(requirementClient.requirementUpdate()).id
    }

    private suspend fun initEvidenceTypeList(
        etl: EvidenceTypeListBase,
        context: Context
    ) {
        if (etl.identifier !in context.processedEvidenceTypeLists) {
            etl.specifiesEvidenceType.forEach { et ->
                if (et.identifier !in context.processedEvidenceTypes) {
                    val evidenceTypeId = et.save()
                    context.processedEvidenceTypes[et.identifier] = evidenceTypeId
                }
            }

            val evidenceTypeListId = etl.save(context)
            context.processedEvidenceTypeLists[etl.identifier] = evidenceTypeListId
        }
    }

    private suspend fun ReferenceFramework.getOrCreate(): FrameworkId {
        return FrameworkGetByIdentifierQueryDTOBase(
            identifier = identifier
        ).invokeWith(frameworkClient.frameworkGetByIdentifier()).item?.id
            ?: FrameworkCreateCommand(
            identifier = identifier,
            name = name
        ).invokeWith(frameworkClient.frameworkCreate()).id
    }

    private suspend fun DataUnitDTO.save(): DataUnitId {
        val existingUnit = DataUnitGetByIdentifierQueryDTOBase(
            identifier = identifier
        ).invokeWith(dataUnitClient.dataUnitGetByIdentifier()).item

        if (existingUnit != null) {
            return DataUnitUpdateCommandDTOBase(
                id = existingUnit.id,
                name = name,
                description = description,
                notation = notation,
                type = type.name.uppercase(),
                options = options?.map { option ->
                    DataUnitOptionUpdateCommandDTOBase(
                        identifier = option.identifier,
                        name = option.name,
                        value = option.value,
                        order = option.order,
                        icon = option.icon,
                        color = option.color
                    )
                }
            ).invokeWith(dataUnitClient.dataUnitUpdate()).id
        }

        return DataUnitCreateCommandDTOBase(
            identifier = identifier,
            name = name,
            description = description,
            notation = notation,
            type = type.name.uppercase(),
            options = options?.map { option ->
                DataUnitOptionCreateCommandDTOBase(
                    identifier = option.identifier,
                    name = option.name,
                    value = option.value,
                    order = option.order,
                    icon = option.icon,
                    color = option.color
                )
            }
        ).invokeWith(dataUnitClient.dataUnitCreate()).id
    }

    private suspend fun InformationConcept.save(context: Context): InformationConceptId {
        val existingConcept = InformationConceptGetByIdentifierQueryDTOBase(
            identifier = identifier
        ).invokeWith(informationConceptClient.conceptGetByIdentifier()).item

        if (this is InformationConceptRef) {
            return existingConcept?.id
                ?: throw IllegalArgumentException("InformationConcept [$identifier] does not exist, cannot reference it")
        }

        if (existingConcept == null) {
            return InformationConceptCreateCommand(
                identifier = identifier,
                name = name,
                hasUnit = context.processedUnits[unit?.identifier],
                description = description,
                expressionOfExpectedValue = expressionOfExpectedValue,
                dependsOn = dependsOn?.map { context.processedConcepts[it]!! }
            ).invokeWith(informationConceptClient.conceptCreate()).id
        }

        return InformationConceptUpdateCommand(
            id = existingConcept.id,
            name = name,
            description = description,
            expressionOfExpectedValue = expressionOfExpectedValue,
            dependsOn = dependsOn?.map { context.processedConcepts[it]!! }
        ).invokeWith(informationConceptClient.conceptUpdate()).id
    }

    private suspend fun EvidenceTypeBase.save(): EvidenceTypeId {
        return EvidenceTypeGetByIdentifierQueryDTOBase(
            identifier = identifier
        ).invokeWith(evidenceTypeClient.evidenceTypeGetByIdentifier()).item?.id
            ?: EvidenceTypeCreateCommand(
            identifier = identifier,
            name = name,
            description = "",
            validityPeriodConstraint = null
        ).invokeWith(evidenceTypeClient.evidenceTypeCreate()).id
    }

    private suspend fun EvidenceTypeListBase.save(context: Context): EvidenceTypeListId {
        return EvidenceTypeListGetByIdentifierQueryDTOBase(
            identifier = identifier
        ).invokeWith(evidenceTypeClient.evidenceTypeListGetByIdentifier()).item?.id
            ?:  EvidenceTypeListCreateCommand(
            identifier = identifier,
            name = name,
            description = description,
            specifiesEvidenceType = specifiesEvidenceType.map { context.processedEvidenceTypes[it.identifier]!! }
        ).invokeWith(evidenceTypeClient.evidenceTypeListCreate()).id
    }

    private fun <K, V> MutableMap<K, V>.putAllNew(map: Map<K, V>) {
        map.forEach { (key, value) ->
            if (key !in this) {
                this[key] = value
            }
        }
    }

    private class Context {
        val processedConcepts = mutableMapOf<String, InformationConceptId>()
        val processedEvidenceTypes = mutableMapOf<String, EvidenceTypeId>()
        val processedEvidenceTypeLists = mutableMapOf<String, EvidenceTypeListId>()
        val processedFrameworks = mutableMapOf<String, FrameworkId>()
        val processedRequirements = mutableMapOf<RequirementIdentifier, RequirementId>()
        val processedUnits = mutableMapOf<String, DataUnitId>()

        val resultRequirements = mutableListOf<RequirementDTOBase>()
    }
}

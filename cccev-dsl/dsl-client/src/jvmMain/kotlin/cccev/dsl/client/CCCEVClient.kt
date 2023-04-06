package cccev.dsl.client

import cccev.dsl.model.DataUnitDTO
import cccev.dsl.model.EvidenceTypeBase
import cccev.dsl.model.EvidenceTypeListBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.Requirement
import cccev.f2.concept.client.InformationConceptClient
import cccev.f2.concept.client.informationConceptClient
import cccev.f2.evidence.type.client.EvidenceTypeClient
import cccev.f2.evidence.type.client.evidenceTypeClient
import cccev.f2.request.client.RequestClient
import cccev.f2.request.client.requestClient
import cccev.f2.requirement.domain.command.RequirementCreateCommandDTOBase
import cccev.f2.unit.client.DataUnitClient
import cccev.f2.unit.client.dataUnitClient
import cccev.f2.unit.domain.command.DataUnitCreateCommandDTOBase
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreateCommand
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreatedEvent
import cccev.s2.requirement.client.RequirementClient
import cccev.s2.requirement.client.requirementClient
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.command.RequirementAddRequirementsCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.requirement.domain.model.RequirementKind
import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.command.DataUnitCreatedEvent
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CCCEVClient(
    val evidenceType: EvidenceTypeClient,
    val informationConcept: InformationConceptClient,
    val request: RequestClient,
    val requirement: RequirementClient,
    val dataUnit: DataUnitClient
) {
    companion object {
        suspend operator fun invoke(url: String, config: f2.client.ktor.http.F2ClientConfigLambda? = null): CCCEVClient {
            val f2Client = F2ClientBuilder.get(url, config = config)
            return CCCEVClient(
                f2Client.evidenceTypeClient().invoke(),
                f2Client.informationConceptClient().invoke(),
                f2Client.requestClient().invoke(),
                f2Client.requirementClient().invoke(),
                f2Client.dataUnitClient().invoke()
            )
        }
    }

    @Suppress("ComplexMethod")
    suspend fun createGraph(requirements: Flow<Requirement>): Flow<RequirementCreatedEvent> {
        val visitedRequirementIdentifiers = mutableSetOf<RequirementIdentifier>()
        val createdUnit = mutableMapOf<String, DataUnitId>()
        val createdConcepts = mutableMapOf<String, InformationConceptId>()
        val createdEvidenceTypes = mutableMapOf<String, EvidenceTypeId>()
        val createdEvidenceTypeLists = mutableMapOf<String, EvidenceTypeListId>()
        val createdRequirements = mutableMapOf<RequirementIdentifier, RequirementId>()

        fun Requirement.flatten(): Flow<Requirement> = flow {
            if (identifier in visitedRequirementIdentifiers) {
                return@flow
            }
            visitedRequirementIdentifiers += identifier!!

            hasRequirement?.forEach { emitAll(it.flatten()) }
            isRequirementOf?.forEach { emitAll(it.flatten()) }
            hasQualifiedRelation?.forEach { emitAll(it.flatten()) }
            emit(this@flatten)
        }

        return requirements.flatMapConcat(Requirement::flatten)
            .map { requirement ->
                requirement.hasConcept?.forEach { concept ->
                    val unit = concept.unit
                    if(unit != null && unit.identifier !in createdUnit) {
                        val unitId = unit.create().id
                        createdUnit[unit.identifier] = unitId
                    }
                    if (concept.identifier !in createdConcepts) {
                        val conceptId = concept.create(createdConcepts, createdUnit).id
                        createdConcepts[concept.identifier] = conceptId
                    }
                }

                requirement.hasEvidenceTypeList?.forEach { etl ->
                    if (etl.identifier !in createdEvidenceTypeLists) {

                        etl.specifiesEvidenceType.forEach { et ->
                            if (et.identifier !in createdEvidenceTypes) {
                                val evidenceTypeId = et.create().id
                                createdEvidenceTypes[et.identifier] = evidenceTypeId
                            }
                        }

                        val evidenceTypeListId = etl.create(createdEvidenceTypes).id
                        createdEvidenceTypeLists[etl.identifier] = evidenceTypeListId
                    }
                }

                val event = RequirementCreateCommandDTOBase(
                    identifier = requirement.identifier,
                    name = requirement.name,
                    description = requirement.description,
                    hasConcept = requirement.hasConcept?.map { createdConcepts[it.identifier]!! }.orEmpty(),
                    hasEvidenceTypeList = requirement.hasEvidenceTypeList?.map { createdEvidenceTypeLists[it.identifier]!! }.orEmpty(),
                    hasRequirement = requirement.hasRequirement?.map { createdRequirements[it.identifier]!! }.orEmpty(),
                    hasQualifiedRelation = requirement.hasQualifiedRelation?.map { createdRequirements[it.identifier]!! },
                    kind = RequirementKind.INFORMATION.name,
                    type = requirement.type?.let {it::class.simpleName}
                ).invokeWith(this.requirement.requirementCreate())

                createdRequirements[event.identifier!!] = event.id

                requirement.isRequirementOf?.forEach { parent ->
                    RequirementAddRequirementsCommand(
                        id = createdRequirements[parent.identifier]!!,
                        requirementIds = listOf(event.id)
                    ).invokeWith(this.requirement.requirementAddRequirements())
                }
                event
            }
    }

    private suspend fun DataUnitDTO.create(): DataUnitCreatedEvent {
       return DataUnitCreateCommandDTOBase(
            name = name,
            description = description,
            notation = notation,
            type = type.name.uppercase()
        ).invokeWith(dataUnit.dataUnitCreate())
    }
    private suspend fun InformationConcept.create(
        conceptIdMap: Map<String, InformationConceptId>,
        unitIdMap: Map<String, DataUnitId>
    ): InformationConceptCreatedEvent {
        return InformationConceptCreateCommand(
            name = name,
            identifier = identifier,
            hasUnit = unitIdMap[unit?.identifier],
            description = description,
            expressionOfExpectedValue = expressionOfExpectedValue,
            dependsOn = dependsOn?.map { conceptIdMap[it]!! },
        ).invokeWith(informationConcept.conceptCreate())
    }

    private suspend fun EvidenceTypeBase.create(): EvidenceTypeCreatedEvent {
        return EvidenceTypeCreateCommand(
            identifier = identifier,
            name = name,
            description = "",
            validityPeriodConstraint = null
        ).invokeWith(evidenceType.evidenceTypeCreate())
    }

    private suspend fun EvidenceTypeListBase.create(evidenceTypeIdMap: Map<String, EvidenceTypeId>): EvidenceTypeListCreatedEvent {
        return EvidenceTypeListCreateCommand(
            identifier = identifier,
            name = name,
            description = description,
            specifiesEvidenceType = specifiesEvidenceType.map { evidenceTypeIdMap[it.identifier]!! }
        ).invokeWith(evidenceType.evidenceTypeListCreate())
    }
}

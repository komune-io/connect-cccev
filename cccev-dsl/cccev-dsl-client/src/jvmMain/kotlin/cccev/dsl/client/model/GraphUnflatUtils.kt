package cccev.dsl.client.model

import cccev.dsl.model.Certification
import cccev.dsl.model.Constraint
import cccev.dsl.model.Criterion
import cccev.dsl.model.DataUnit
import cccev.dsl.model.DataUnitOption
import cccev.dsl.model.DataUnitType
import cccev.dsl.model.EvidenceTypeBase
import cccev.dsl.model.EvidenceTypeListBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptDTO
import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.InformationRequirement
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementCertification
import cccev.dsl.model.SupportedValue
import cccev.dsl.model.nullIfEmpty
import cccev.f2.CccevFlatGraph
import cccev.f2.certification.model.CertificationFlat
import cccev.f2.certification.model.RequirementCertificationFlat
import cccev.f2.certification.model.SupportedValueFlat
import cccev.f2.certification.query.CertificationGetResult
import cccev.f2.concept.model.InformationConceptFlat
import cccev.f2.concept.query.InformationConceptGetByIdentifierResult
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.requirement.model.RequirementFlat
import cccev.dsl.model.RequirementKind
import cccev.f2.requirement.query.RequirementGetByIdentifierResult
import cccev.f2.requirement.query.RequirementGetResult
import cccev.f2.unit.model.DataUnitFlat

class NotFoundException(msg: String, id: String): Exception("$msg with id $id not found")

fun CertificationGetResult.unflatten(): Certification? {
    return certification?.unflatten(graph)
}

fun CertificationFlat.unflatten(graph: CccevFlatGraph): Certification {
    val requirementCertifications =  requirementCertificationIds.map { requirementCertificationId ->
        graph.requirementCertifications[requirementCertificationId]
            ?.unflatten(graph)
            ?: throw NotFoundException("RequirementCertification", requirementCertificationId)
    }
    return Certification(
        id = id,
        requirementCertifications = requirementCertifications
    )
}

fun RequirementCertificationFlat.unflatten(graph: CccevFlatGraph): RequirementCertification {
    val subCertificationIds =  subCertificationIds.map { subCertificationId ->
        graph.requirementCertifications[subCertificationId]
            ?.unflatten(graph)
            ?: throw NotFoundException("RequirementCertification", subCertificationId)

    }
    val values = valueIds.map { valueId ->
        graph.supportedValues[valueId]
            ?.unflatten(graph)
            ?: throw NotFoundException("SupportedValue", valueId)

    }
    return RequirementCertification(
        id = id,
        requirement = graph.requirements[requirementIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("Requirement", requirementIdentifier),
        subCertifications = subCertificationIds,
        values = values,
        isEnabled = isEnabled,
        isValidated = isValidated,
        isFulfilled = isFulfilled,
        hasAllValues = hasAllValues
    )
}

fun SupportedValueFlat.unflatten(graph: CccevFlatGraph): SupportedValue {
//    val concepts = graph.concepts[conceptIdentifier]
//        ?.unflatten(graph)
//        ?: throw NotFoundException("InformationConcept", conceptIdentifier)
    return SupportedValue(
//        id = id,
        //TODO FIX that
        identifier = id,
        value = value,
//        query = query,
        providesValueFor = conceptIdentifier,


    )
}

fun InformationConceptGetByIdentifierResult.unflatten(): InformationConceptDTO {
    return item?.unflatten(graph)
        ?: throw NotFoundException("InformationConcept", item?.id!!)
}

fun InformationConceptFlat.unflatten(graph: CccevFlatGraph): InformationConceptDTO {
    val unit = graph.units[unitIdentifier]
        ?.unflatten(graph)
        ?: throw NotFoundException("DataUnit", unitIdentifier)

    val dependencies = dependsOn?.map { dependencyIdentifier ->
        graph.concepts[dependencyIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", dependencyIdentifier)
    }?.map { it.id }
    return InformationConcept(
        id = id,
        identifier = identifier,
        name = name,
        unit = unit,
        description = description,
        expressionOfExpectedValue = expressionOfExpectedValue,
        dependsOn = dependencies
    )
}

fun RequirementGetResult.unflatten(): Requirement {
    return item?.unflatten(graph)
        ?: throw NotFoundException("Requirement", item?.id!!)
}

fun RequirementGetByIdentifierResult.unflatten(): Requirement {
    return item?.unflatten(graph)
        ?: throw NotFoundException("Requirement", item?.id!!)
}


fun RequirementFlat.unflatten(graph: CccevFlatGraph): Requirement {
    val subRequirements = subRequirementIds.map {
        graph.requirements[it]?.unflatten(graph)
            ?: throw NotFoundException("Requirement", it)
    }.ifEmpty { null }
    val concepts = conceptIdentifiers.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.ifEmpty { null }
    val evidenceTypes = evidenceTypeIds.map {
        graph.evidenceTypes[it]?.unflatten(graph)
            ?: throw NotFoundException("EvidenceType", it)
    }.ifEmpty { null }


    val evidenceTypeLists =  if(!evidenceTypes.isNullOrEmpty()) {
        listOf(EvidenceTypeListBase(
            name = "default",
            description = "default",
            identifier = "default",
            specifiesEvidenceType = evidenceTypes
        ))
    } else null



    val enablingConditionDependencies = enablingConditionDependencies.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.map { it.identifier }.ifEmpty { null }
    val validatingConditionDependencies = validatingConditionDependencies.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.map { it.identifier }.ifEmpty { null }
    return when(RequirementKind.valueOf(kind)) {
        RequirementKind.CONSTRAINT -> asConstant(
            enablingConditionDependencies,
            validatingConditionDependencies,
            subRequirements,
            concepts,
            evidenceTypeLists
        )
        RequirementKind.CRITERION -> asCriterion(
            enablingConditionDependencies,
            validatingConditionDependencies,
            subRequirements,
            concepts,
            evidenceTypeLists
        )
        RequirementKind.INFORMATION -> asInformationRequirement(
            enablingConditionDependencies,
            validatingConditionDependencies,
            subRequirements,
            concepts,
            evidenceTypeLists
        )
    }
}

private fun RequirementFlat.asInformationRequirement(
    enablingConditionDependencies: List<InformationConceptIdentifier>?,
    validatingConditionDependencies: List<InformationConceptIdentifier>?,
    subRequirements: List<Requirement>?,
    concepts: List<InformationConceptDTO>?,
    evidenceTypeLists: List<EvidenceTypeListBase>?
) = InformationRequirement(
    id = id,
    identifier = identifier,
    description = description,
    type = type,
    name = name,
//        subRequirements = subRequirements.toMutableList(),
    enablingCondition = enablingCondition,
    order = order,
    properties = properties,
    required = required,
    hasConcept = concepts,
//        evidenceTypes = evidenceTypes,
    enablingConditionDependencies = enablingConditionDependencies,
    validatingCondition = validatingCondition,
    validatingConditionDependencies = validatingConditionDependencies,
    evidenceValidatingCondition = evidenceValidatingCondition,
    hasRequirement = subRequirements,
    isDerivedFrom = null,
    hasEvidenceTypeList = evidenceTypeLists,
    isRequirementOf = null,
)

private fun RequirementFlat.asCriterion(
    enablingConditionDependencies: List<InformationConceptIdentifier>?,
    validatingConditionDependencies: List<InformationConceptIdentifier>?,
    subRequirements: List<Requirement>?,
    concepts: List<InformationConceptDTO>?,
    evidenceTypeLists: List<EvidenceTypeListBase>?
) = Criterion(
    id = id,
    identifier = identifier,
    description = description,
    type = type,
    name = name,
    enablingCondition = enablingCondition,
    order = order,
    properties = properties,
    required = required,
    hasConcept = concepts,
    enablingConditionDependencies = enablingConditionDependencies,
    validatingCondition = validatingCondition,
    validatingConditionDependencies = validatingConditionDependencies,
    hasRequirement = subRequirements,
    hasEvidenceTypeList = evidenceTypeLists,
    isDerivedFrom = null,
    isRequirementOf = null,
    evidenceValidatingCondition = evidenceValidatingCondition
)

private fun RequirementFlat.asConstant(
    enablingConditionDependencies: List<InformationConceptIdentifier>?,
    validatingConditionDependencies: List<InformationConceptIdentifier>?,
    subRequirements: List<Requirement>?,
    concepts: List<InformationConceptDTO>?,
    evidenceTypeLists: List<EvidenceTypeListBase>?
) = Constraint(
    id = id,
    identifier = identifier,
    description = description,
    type = type,
    name = name,
    enablingCondition = enablingCondition,
    order = order,
    properties = properties,
    required = required,
    enablingConditionDependencies = enablingConditionDependencies,
    validatingCondition = validatingCondition,
    validatingConditionDependencies = validatingConditionDependencies,
    hasRequirement = subRequirements,
    isDerivedFrom = null,
    hasConcept = concepts,
    hasEvidenceTypeList = evidenceTypeLists,
    isRequirementOf = null,
    evidenceValidatingCondition = evidenceValidatingCondition
)

fun DataUnitFlat.unflatten(graph: CccevFlatGraph): DataUnit {
    return DataUnit(
        identifier = identifier,
        name = name,
        description = description,
        notation = notation,
        type = DataUnitType.valueOf(type),
        options = optionIdentifiers?.map {
            graph.unitOptions[it]?.unflatten()
                ?: throw NotFoundException("DataUnitOption", it)
        }.nullIfEmpty()
    )
}

fun DataUnitOption.unflatten(): DataUnitOption {
    return DataUnitOption(
        identifier = identifier,
        name = name,
        value = value,
        order = order,
        icon = icon,
        color = color
    )
}

fun EvidenceTypeFlat.unflatten(graph: CccevFlatGraph): EvidenceTypeBase {
    return EvidenceTypeBase(
        identifier = identifier,
        name = name,
        supportConcept = conceptIdentifiers.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("EvidenceType", it)
        }
    )
}

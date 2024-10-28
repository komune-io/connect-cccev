package cccev.dsl.client.model

import cccev.dsl.model.Certification
import cccev.dsl.model.Constraint
import cccev.dsl.model.Criterion
import cccev.dsl.model.DataUnit
import cccev.dsl.model.DataUnitOption
import cccev.dsl.model.DataUnitType
import cccev.dsl.model.EvidenceType
import cccev.dsl.model.EvidenceTypeBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.InformationRequirement
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementCertification
import cccev.dsl.model.SupportedValue
import cccev.f2.CccevFlatGraph
import cccev.f2.certification.model.CertificationFlat
import cccev.f2.certification.model.RequirementCertificationFlat
import cccev.f2.certification.model.SupportedValueFlat
import cccev.f2.concept.model.InformationConceptFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.requirement.model.RequirementFlat
import cccev.f2.requirement.model.RequirementKind
import cccev.f2.unit.model.DataUnitFlat

class NotFoundException(msg: String, id: String): Exception()

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

fun InformationConceptFlat.unflatten(graph: CccevFlatGraph): InformationConcept {
    val unit = graph.units[unitIdentifier]
        ?.unflatten(graph)
        ?: throw NotFoundException("DataUnit", unitIdentifier)

    val dependencies = dependsOn.map { dependencyIdentifier ->
        graph.concepts[dependencyIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", dependencyIdentifier)
    }.map { it.id }
    return InformationConceptBase(
        id = id,
        identifier = identifier,
        name = name,
        unit = unit,
        description = description,
        expressionOfExpectedValue = expressionOfExpectedValue,
        dependsOn = dependencies
    )
}

fun RequirementFlat.unflatten(graph: CccevFlatGraph): Requirement {
    val subRequirements = subRequirementIds.map {
        graph.requirements[it]?.unflatten(graph)
            ?: throw NotFoundException("Requirement", it)
    }
    val concepts = conceptIdentifiers.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }
    val evidenceTypes = evidenceTypeIds.map {
        graph.evidenceTypes[it]?.unflatten(graph)
            ?: throw NotFoundException("EvidenceType", it)
    }
    val enablingConditionDependencies = enablingConditionDependencies.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.map { it.id }
    val validatingConditionDependencies = validatingConditionDependencies.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.map { it.id }
    return when(RequirementKind.valueOf(kind)) {
        RequirementKind.CONSTRAINT -> Constraint(
            id = id,
            identifier = identifier,
//        kind = RequirementKind.valueOf(kind)
            description = description,
            type = type,
            name = name,
//        subRequirements = subRequirements.toMutableList(),
            enablingCondition = enablingCondition,
            order = order,
            properties = properties,
            required = required,
//        concepts = concepts,
//        evidenceTypes = evidenceTypes,
            enablingConditionDependencies = enablingConditionDependencies,
            validatingCondition = validatingCondition,
            validatingConditionDependencies = validatingConditionDependencies,
            hasRequirement = emptyList(),
            isDerivedFrom = emptyList(),
            hasConcept = emptyList(),
            hasEvidenceTypeList = emptyList(),
            isRequirementOf =  emptyList(),
            evidenceValidatingCondition = evidenceValidatingCondition
        )
        RequirementKind.CRITERION -> Criterion(
            id = id,
            identifier = identifier,
//        kind = RequirementKind.valueOf(kind)
            description = description,
            type = type,
            name = name,
//        subRequirements = subRequirements.toMutableList(),
            enablingCondition = enablingCondition,
            order = order,
            properties = properties,
            required = required,
//        concepts = concepts,
//        evidenceTypes = evidenceTypes,
            enablingConditionDependencies = enablingConditionDependencies,
            validatingCondition = validatingCondition,
            validatingConditionDependencies = validatingConditionDependencies,
            hasRequirement = emptyList(),
            isDerivedFrom = emptyList(),
            hasConcept = emptyList(),
            hasEvidenceTypeList = emptyList(),
            isRequirementOf =  emptyList(),
            evidenceValidatingCondition = evidenceValidatingCondition
        )
        RequirementKind.INFORMATION -> InformationRequirement(
            id = id,
            identifier = identifier,
//        kind = RequirementKind.valueOf(kind)
            description = description,
            type = type,
            name = name,
//        subRequirements = subRequirements.toMutableList(),
            enablingCondition = enablingCondition,
            order = order,
            properties = properties,
            required = required,
//        concepts = concepts,
//        evidenceTypes = evidenceTypes,
            enablingConditionDependencies = enablingConditionDependencies,
            validatingCondition = validatingCondition,
            validatingConditionDependencies = validatingConditionDependencies,
            evidenceValidatingCondition = evidenceValidatingCondition,
            hasRequirement = emptyList(),
            isDerivedFrom = emptyList(),
            hasConcept = emptyList(),
            hasEvidenceTypeList = emptyList(),
            isRequirementOf =  emptyList(),
        )
    }
}

fun DataUnitFlat.unflatten(graph: CccevFlatGraph): DataUnit {
    return DataUnit(
        identifier = identifier,
        name = name,
        description = description,
        notation = notation,
        type = DataUnitType.valueOf(type),
        options = optionIdentifiers.map {
            graph.unitOptions[it]?.unflatten(graph)
                ?: throw NotFoundException("DataUnitOption", it)
        }
    )
}

fun DataUnitOption.unflatten(graph: CccevFlatGraph): DataUnitOption {
    return DataUnitOption(
        identifier = identifier,
        name = name,
        value = value,
        order = order,
        icon = icon,
        color = color
    )
}

fun EvidenceTypeFlat.unflatten(graph: CccevFlatGraph): EvidenceType {
    return EvidenceTypeBase(
        identifier = identifier,
        name = name,
        supportConcept = conceptIdentifiers.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        }
    )
}

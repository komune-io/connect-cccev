package cccev.dsl.client.model

import cccev.f2.certification.domain.model.Certification
import cccev.f2.certification.domain.model.CertificationFlat
import cccev.f2.certification.domain.model.RequirementCertification
import cccev.f2.certification.domain.model.RequirementCertificationFlat
import cccev.f2.certification.domain.model.SupportedValue
import cccev.f2.certification.domain.model.SupportedValueFlat
import cccev.f2.certification.domain.query.CertificationGetResult
import cccev.f2.commons.CertificationFlatGraph
import cccev.f2.concept.domain.model.InformationConceptFlat
import cccev.f2.requirement.domain.model.RequirementFlat
import cccev.f2.unit.domain.model.DataUnitFlat
import cccev.f2.unit.domain.model.DataUnitOption
import cccev.s2.concept.domain.model.InformationConcept
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.model.Requirement
import cccev.s2.requirement.domain.model.RequirementKind
import cccev.s2.unit.domain.model.DataUnit
import cccev.s2.unit.domain.model.DataUnitType

class NotFoundException(type: String, id: String) : RuntimeException("Not found $type with id $id")

fun CertificationFlat.unflatten(graph: CertificationFlatGraph): Certification {
    val requirementCertifications = requirementCertificationIds.map { requirementCertificationId ->
        graph.requirementCertifications[requirementCertificationId]
            ?.unflatten(graph)
            ?: throw NotFoundException("RequirementCertification", requirementCertificationId)
    }
    return Certification(
        id = id,
        requirementCertifications = requirementCertifications,
    )
}

fun RequirementCertificationFlat.unflatten(graph: CertificationFlatGraph): RequirementCertification {
    val requirement = graph.requirements[requirementIdentifier]
        ?.unflatten(graph)
        ?: throw NotFoundException("Requirement", requirementIdentifier)
    val requirementCertification = subCertificationIds.map { subCertificationId ->
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
        requirement = TODO(),
        subCertifications = requirementCertification,
        values = TODO(),
        isEnabled = isEnabled,
        isValidated = isValidated,
        isFulfilled = isFulfilled,
        hasAllValues = hasAllValues
    )
}

fun SupportedValueFlat.unflatten(graph: CertificationFlatGraph): SupportedValue {
    val concept = graph.concepts[conceptIdentifier]
        ?.unflatten(graph)
        ?: throw NotFoundException("InformationConcept", conceptIdentifier)
    return SupportedValue(
        id = id,
        value = value,
        concept = concept,
    )
}

fun InformationConceptFlat.unflatten(graph: CertificationFlatGraph): InformationConcept {
   val unit = graph.units[unitIdentifier]
       ?.unflatten(graph)
       ?: throw NotFoundException("DataUnit", unitIdentifier)
    val dependsOn = dependsOn?.map { dependencyIdentifier ->
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
        dependsOn = dependsOn
    )
}

fun RequirementFlat.unflatten(graph: CertificationFlatGraph): Requirement {
    val subRequirements = hasRequirement.map {
        graph.requirements[it]?.unflatten(graph)
            ?: throw NotFoundException("Requirement", it)
    }
    val hasConcept = hasConcept.map {
        graph.concepts[it]?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", it)
    }.toMutableList()


    return Requirement(
        id = id,
        identifier = identifier,
        kind = RequirementKind.valueOf(kind),
        description = description,
        type = type,
        name = name,
        hasQualifiedRelation = emptyMap(),
        hasConcept = hasConcept,
        hasEvidenceTypeList = mutableListOf(),
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        },
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        },
        order = order,
        properties = properties,
        hasRequirement = subRequirements,
        isDerivedFrom = emptyList(),
        isRequirementOf = emptyList(),
        state = RequirementState.CREATED
    )
}


fun DataUnitFlat.unflatten(graph: CertificationFlatGraph): DataUnit {

    val options = optionIdentifiers?.map {
        graph.unitOptions[it]
            ?.unflatten(graph)
            ?: throw NotFoundException("DataUnitOption", it)
    }?.toMutableList()
    return DataUnit(
        id = id,
        identifier = identifier,
        name = name,
        description = description,
        notation = notation,
        type = DataUnitType.valueOf(type),
        options = options
    )
}

fun DataUnitOption.unflatten(graph: CertificationFlatGraph): cccev.s2.unit.domain.model.DataUnitOption {
    return cccev.s2.unit.domain.model.DataUnitOption(
        id = id,
        identifier = identifier,
        name = name,
        value = value,
        order = order,
        icon = icon,
        color = color
    )
}

fun CertificationGetResult.toCertificationFlatGraph() = certification?.let {
    CertificationFlatGraph().also { graph ->
        graph.certification = it
        graph.requirementCertifications.putAll(requirementCertifications)
        graph.requirements.putAll(requirements)
        graph.concepts.putAll(concepts)
        graph.units.putAll(units)
        graph.unitOptions.putAll(unitOptions)
        graph.supportedValues.putAll(supportedValues)
    }
}

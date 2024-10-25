package cccev.dsl.client.model

import cccev.core.certification.entity.Certification
import cccev.core.certification.entity.RequirementCertification
import cccev.core.certification.entity.SupportedValue
import cccev.core.concept.entity.InformationConcept
import cccev.core.evidencetype.entity.EvidenceType
import cccev.core.requirement.entity.Requirement
import cccev.core.requirement.model.RequirementKind
import cccev.core.unit.entity.DataUnit
import cccev.core.unit.entity.DataUnitOption
import cccev.core.unit.model.DataUnitType
import cccev.f2.certification.domain.model.CertificationFlat
import cccev.f2.certification.domain.model.RequirementCertificationFlat
import cccev.f2.certification.domain.model.SupportedValueFlat
import cccev.f2.commons.CccevFlatGraph
import cccev.f2.concept.domain.model.InformationConceptFlat
import cccev.f2.evidence.type.domain.model.EvidenceTypeFlat
import cccev.f2.requirement.domain.model.RequirementFlat
import cccev.f2.unit.domain.model.DataUnitFlat
import f2.spring.exception.NotFoundException

fun CertificationFlat.unflatten(graph: CccevFlatGraph): Certification {
    return Certification().also { certification ->
        certification.id = id
        requirementCertificationIds.forEach { requirementCertificationId ->
            val requirementCertification = graph.requirementCertifications[requirementCertificationId]
                ?.unflatten(graph)
                ?: throw NotFoundException("RequirementCertification", requirementCertificationId)

            certification.requirementCertifications.add(requirementCertification)
        }
    }
}

fun RequirementCertificationFlat.unflatten(graph: CccevFlatGraph): RequirementCertification {
    return RequirementCertification().also { requirementCertification ->
        requirementCertification.id = id
        requirementCertification.requirement = graph.requirements[requirementIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("Requirement", requirementIdentifier)

        subCertificationIds.forEach { subCertificationId ->
            val subCertification = graph.requirementCertifications[subCertificationId]
                ?.unflatten(graph)
                ?: throw NotFoundException("RequirementCertification", subCertificationId)

            requirementCertification.subCertifications.add(subCertification)
        }

        valueIds.forEach { valueId ->
            val value = graph.supportedValues[valueId]
                ?.unflatten(graph)
                ?: throw NotFoundException("SupportedValue", valueId)

            requirementCertification.values.add(value)
        }

        requirementCertification.isEnabled = isEnabled
        requirementCertification.isValidated = isValidated
        requirementCertification.isFulfilled = isFulfilled
        requirementCertification.hasAllValues = hasAllValues
    }
}

fun SupportedValueFlat.unflatten(graph: CccevFlatGraph): SupportedValue {
    return SupportedValue().also { supportedValue ->
        supportedValue.id = id
        supportedValue.value = value
        supportedValue.concept = graph.concepts[conceptIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("InformationConcept", conceptIdentifier)
    }
}

fun InformationConceptFlat.unflatten(graph: CccevFlatGraph): InformationConcept {
    return InformationConcept().also { concept ->
        concept.id = id
        concept.identifier = identifier
        concept.name = name
        concept.unit = graph.units[unitIdentifier]
            ?.unflatten(graph)
            ?: throw NotFoundException("DataUnit", unitIdentifier)
        concept.description = description
        concept.expressionOfExpectedValue = expressionOfExpectedValue
        concept.dependencies = dependsOn.map { dependencyIdentifier ->
            graph.concepts[dependencyIdentifier]
                ?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", dependencyIdentifier)
        }.toMutableList()
    }
}

fun RequirementFlat.unflatten(graph: CccevFlatGraph): Requirement {
    val subRequirements = subRequirementIds.map {
        graph.requirements[it]?.unflatten(graph)
            ?: throw NotFoundException("Requirement", it)
    }

    return Requirement().also { requirement ->
        requirement.id = id
        requirement.identifier = identifier
        requirement.kind = RequirementKind.valueOf(kind)
        requirement.description = description
        requirement.type = type
        requirement.name = name
        requirement.subRequirements = subRequirements.toMutableList()
        requirement.concepts = conceptIdentifiers.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        }.toMutableList()
        requirement.evidenceTypes = evidenceTypeIds.map {
            graph.evidenceTypes[it]?.unflatten(graph)
                ?: throw NotFoundException("EvidenceType", it)
        }.toMutableList()
        requirement.enablingCondition = enablingCondition
        requirement.enablingConditionDependencies = enablingConditionDependencies.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        }.toMutableList()
        requirement.required = required
        requirement.validatingCondition = validatingCondition
        requirement.validatingConditionDependencies = validatingConditionDependencies.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        }.toMutableList()
        requirement.order = order
        requirement.properties = properties
    }
}

fun DataUnitFlat.unflatten(graph: CccevFlatGraph): DataUnit {
    return DataUnit().also { unit ->
        unit.id = id
        unit.identifier = identifier
        unit.name = name
        unit.description = description
        unit.notation = notation
        unit.type = DataUnitType.valueOf(type)
        unit.options = optionIdentifiers.map {
            graph.unitOptions[it]?.unflatten(graph)
                ?: throw NotFoundException("DataUnitOption", it)
        }.toMutableList()
    }
}

fun cccev.f2.unit.domain.model.DataUnitOption.unflatten(graph: CccevFlatGraph): DataUnitOption {
    return DataUnitOption().also { option ->
        option.id = id
        option.identifier = identifier
        option.name = name
        option.value = value
        option.order = order
        option.icon = icon
        option.color = color
    }
}

fun EvidenceTypeFlat.unflatten(graph: CccevFlatGraph): EvidenceType {
    return EvidenceType().also { evidenceType ->
        evidenceType.id = id
        evidenceType.name = name
        evidenceType.concepts = conceptIdentifiers.map {
            graph.concepts[it]?.unflatten(graph)
                ?: throw NotFoundException("InformationConcept", it)
        }.toMutableList()
    }
}

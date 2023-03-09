package cccev.s2.requirement.api.entity

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.s2.requirement.domain.model.Requirement

fun RequirementEntity.toRequirement() = Requirement(
    id = id,
    identifier = identifier,
    kind = kind,
    description = description,
    name = name,
    hasRequirement = hasRequirement.map { it.id },
    hasConcept = hasConcept.map { it.id },
    hasEvidenceTypeList = hasEvidenceTypeList.map { it.id },
//    isRequirementOf = isRequirementOf,
    isRequirementOf = emptyList(),
    hasQualifiedRelation = hasQualifiedRelation.map { it.id },
    state = status
)

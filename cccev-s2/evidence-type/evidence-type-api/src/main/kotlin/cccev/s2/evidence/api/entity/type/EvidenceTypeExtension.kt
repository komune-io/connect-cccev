package cccev.s2.evidence.api.entity.type

import cccev.s2.evidence.domain.model.EvidenceType

fun EvidenceTypeEntity.toEvidenceType() = EvidenceType(
    id = id,
    name = name,
    description = description,
    validityPeriodConstraint = validityPeriodConstraint
)

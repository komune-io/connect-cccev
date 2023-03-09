package cccev.s2.evidence.api.entity.list

import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEntity
import cccev.s2.evidence.api.entity.type.toEvidenceType
import cccev.s2.evidence.domain.model.EvidenceType
import cccev.s2.evidence.domain.model.EvidenceTypeList

fun EvidenceTypeListEntity.toEvidenceTypeList() = EvidenceTypeList(
    id = id,
    name = name,
    description = description,
    specifiesEvidenceType = specifiesEvidenceType.map(EvidenceTypeEntity::toEvidenceType)
)

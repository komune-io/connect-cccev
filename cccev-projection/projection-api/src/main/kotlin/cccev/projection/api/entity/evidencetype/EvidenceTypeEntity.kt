package cccev.projection.api.entity.evidencetype

import cccev.projection.api.entity.EntityBase
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeState
import org.springframework.data.neo4j.core.schema.Node

@Node("EvidenceType")
class EvidenceTypeEntity: EntityBase<EvidenceTypeId, EvidenceTypeState>() {
    lateinit var name: String

    lateinit var description: String

    var validityPeriodConstraint: Long? = null
}

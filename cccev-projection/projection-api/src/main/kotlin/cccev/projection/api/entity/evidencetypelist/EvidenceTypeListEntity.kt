package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.EntityBase
import cccev.projection.api.entity.NodeLabel
import cccev.projection.api.entity.Relation
import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.EvidenceTypeListState
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node(NodeLabel.EVIDENCE_TYPE_LIST)
class EvidenceTypeListEntity: EntityBase<EvidenceTypeListId, EvidenceTypeListState>() {
    var identifier: String? = null

    lateinit var name: String

    lateinit var description: String

    @Relationship(Relation.SPECIFIES_EVIDENCE_TYPE)
    lateinit var specifiesEvidenceType: MutableList<EvidenceTypeEntity>
}

package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.Relation
import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.EvidenceTypeListState
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.UUID

@Node("EvidenceTypeList")
class EvidenceTypeListEntity {
    @Id
    val id: EvidenceTypeListId = UUID.randomUUID().toString()

    lateinit var name: String

    lateinit var description: String

    @Relationship(Relation.SPECIFIES_EVIDENCE_TYPE)
    lateinit var specifiesEvidenceType: List<EvidenceTypeEntity>

    lateinit var status: EvidenceTypeListState
}

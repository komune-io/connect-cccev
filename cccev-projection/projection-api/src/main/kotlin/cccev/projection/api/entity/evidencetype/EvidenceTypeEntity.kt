package cccev.projection.api.entity.evidencetype

import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeState
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import java.util.UUID

@Node("EvidenceType")
class EvidenceTypeEntity: WithS2Id<EvidenceTypeId>, WithS2State<EvidenceTypeState> {
    @Id
    var id: EvidenceTypeId = UUID.randomUUID().toString()

    lateinit var name: String

    lateinit var description: String

    var validityPeriodConstraint: Long? = null

    lateinit var status: EvidenceTypeState

    override fun s2Id() = id
    override fun s2State() = status
}

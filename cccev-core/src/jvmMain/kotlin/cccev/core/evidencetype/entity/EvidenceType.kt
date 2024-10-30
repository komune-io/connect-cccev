package cccev.core.evidencetype.entity

import cccev.core.concept.entity.InformationConcept
import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.EvidenceTypeIdentifier
import java.util.UUID
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(EvidenceType.LABEL)
class EvidenceType {
    companion object {
        const val LABEL = "EvidenceType"
        const val SUPPORTS_CONCEPT = "SUPPORTS_CONCEPT"
    }
    @Id
    lateinit var id: EvidenceTypeId

    // TODO This is a temporary solution to avoid breaking the tests
    var identifier: EvidenceTypeIdentifier = UUID.randomUUID().toString()

    lateinit var name: String

    @Relationship(SUPPORTS_CONCEPT)
    var concepts: MutableList<InformationConcept> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

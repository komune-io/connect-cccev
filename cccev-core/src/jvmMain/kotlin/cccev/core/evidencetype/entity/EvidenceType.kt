package cccev.core.evidencetype.entity

import cccev.core.concept.entity.InformationConcept
import cccev.core.evidencetype.model.EvidenceTypeId
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

    lateinit var name: String

    @Relationship(SUPPORTS_CONCEPT)
    var concepts: MutableList<InformationConcept> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

package cccev.core.certification.entity

import cccev.commons.utils.parseJsonTo
import cccev.commons.utils.toJson
import cccev.core.evidencetype.entity.EvidenceType
import cccev.dsl.model.EvidenceId
import io.komune.fs.s2.file.domain.model.FilePath
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(Evidence.LABEL)
class Evidence {
    companion object {
        const val LABEL = "Evidence"
        const val IS_CONFORMANT_TO = "IS_CONFORMANT_TO"
    }
    @Id
    lateinit var id: EvidenceId

    private lateinit var fileJson: String
    var file: FilePath
        get() = fileJson.parseJsonTo<FilePath>()
        set(value) { fileJson = value.toJson() }

    @Relationship(IS_CONFORMANT_TO)
    lateinit var evidenceType: EvidenceType

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

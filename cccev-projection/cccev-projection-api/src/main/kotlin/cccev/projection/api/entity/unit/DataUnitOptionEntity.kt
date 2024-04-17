package cccev.projection.api.entity.unit

import cccev.s2.unit.domain.DataUnitOptionId
import cccev.s2.unit.domain.DataUnitOptionIdentifier
import io.komune.fs.s2.file.domain.model.FilePath
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.neo4j.core.schema.Node

@Node(DataUnitOptionEntity.LABEL)
@org.neo4j.ogm.annotation.NodeEntity(DataUnitOptionEntity.LABEL)
data class DataUnitOptionEntity(
    @Id
    @org.neo4j.ogm.annotation.Id
    val id: DataUnitOptionId,
    val identifier: DataUnitOptionIdentifier,
    @Version
    @org.neo4j.ogm.annotation.Version
    var version: Long = 0,
    @CreatedDate
    var creationDate: Long = 0,
    @LastModifiedDate
    var lastModificationDate: Long = 0,
    val name: String,
    val value: String,
    val order: Int,
    val icon: FilePath?,
    val color: String?
) {
    companion object {
        const val LABEL = "DataUnitOption"
    }
}

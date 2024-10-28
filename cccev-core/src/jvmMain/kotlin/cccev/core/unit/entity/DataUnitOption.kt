package cccev.core.unit.entity

import cccev.commons.utils.parseJsonTo
import cccev.commons.utils.toJson
import cccev.dsl.model.DataUnitOptionId
import cccev.dsl.model.DataUnitOptionIdentifier
import io.komune.fs.s2.file.domain.model.FilePath
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Version

@NodeEntity(DataUnitOption.LABEL)
class DataUnitOption {
    companion object {
        const val LABEL = "DataUnitOption"
    }

    @Id
    lateinit var id: DataUnitOptionId

    var identifier: DataUnitOptionIdentifier = id

    lateinit var name: String

    lateinit var value: String

    var order: Int = 0

    private var iconJson: String? = null
    var icon: FilePath?
        get() = iconJson?.parseJsonTo<FilePath>()
        set(value) { iconJson = value?.toJson() }

    var color: String? = null

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

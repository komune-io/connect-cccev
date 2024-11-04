package cccev.core.unit.entity

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(DataUnitEntity.LABEL)
class DataUnitEntity {
    companion object {
        const val LABEL = "DataUnit"
        const val HAS_OPTION = "HAS_OPTION"
    }

    @Id
    lateinit var id: DataUnitId

    lateinit var identifier: DataUnitIdentifier

    lateinit var name: String

    lateinit var description: String

    var notation: String? = null

    lateinit var type: DataUnitType

    @Relationship(HAS_OPTION)
    var options: MutableList<DataUnitOptionEntity> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

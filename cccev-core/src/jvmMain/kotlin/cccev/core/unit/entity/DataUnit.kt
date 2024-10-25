package cccev.core.unit.entity

import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import cccev.core.unit.model.DataUnitType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(DataUnit.LABEL)
class DataUnit {
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
    var options: MutableList<DataUnitOption> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}

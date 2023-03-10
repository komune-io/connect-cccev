package cccev.projection.api.entity.unit

import cccev.projection.api.entity.EntityBase
import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.DataUnitState
import cccev.s2.unit.domain.model.DataUnitType
import org.springframework.data.neo4j.core.schema.Node

@Node("DataUnit")
class DataUnitEntity: EntityBase<DataUnitId, DataUnitState>() {
    lateinit var name: String

    lateinit var description: String

    var notation: String? = null

    lateinit var type: DataUnitType
}

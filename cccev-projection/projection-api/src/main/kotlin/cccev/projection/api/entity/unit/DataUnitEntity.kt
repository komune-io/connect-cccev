package cccev.projection.api.entity.unit

import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.DataUnitState
import cccev.s2.unit.domain.model.DataUnitType
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import java.util.UUID

@Node("DataUnit")
class DataUnitEntity: WithS2Id<DataUnitId>, WithS2State<DataUnitState> {
    @Id
    var id: DataUnitId = UUID.randomUUID().toString()

    lateinit var name: String

    lateinit var description: String

    var notation: String? = null

    lateinit var type: DataUnitType

    lateinit var status: DataUnitState

    override fun s2Id() = id
    override fun s2State() = status
}

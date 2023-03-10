package cccev.projection.api.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.neo4j.core.schema.Id
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

abstract class EntityBase<ID: Any, STATE: S2State>: WithS2Id<ID>, WithS2State<STATE> {
    @Id
    open lateinit var id: ID

    open lateinit var status: STATE

    @Version
    var version: Long = 0

    @CreatedDate
    var creationDate: Long = 0

    @LastModifiedDate
    var lastModificationDate: Long = 0

    override fun s2Id() = id
    override fun s2State() = status
}

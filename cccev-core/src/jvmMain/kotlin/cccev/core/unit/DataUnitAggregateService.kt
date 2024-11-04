package cccev.core.unit

import cccev.core.unit.entity.DataUnitEntity
import cccev.core.unit.entity.DataUnitOptionEntity
import cccev.f2.unit.command.DataUnitCreateCommand
import cccev.f2.unit.command.DataUnitCreatedEvent
import cccev.f2.unit.command.DataUnitUpdateCommand
import cccev.f2.unit.command.DataUnitUpdatedEvent
import cccev.infra.neo4j.removeSeveredRelations
import cccev.infra.neo4j.transaction
import f2.spring.exception.NotFoundException
import java.util.UUID
import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class DataUnitAggregateService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val sessionFactory: SessionFactory
) {
    suspend fun create(command: DataUnitCreateCommand): DataUnitCreatedEvent = sessionFactory.transaction { session, _ ->
        val unit = DataUnitEntity().apply {
            id = UUID.randomUUID().toString()
            identifier = command.identifier
            name = command.name
            description = command.description
            notation = command.notation
            type = command.type
            options = command.options?.map { option ->
                DataUnitOptionEntity().apply {
                    id = UUID.randomUUID().toString()
                    identifier = option.identifier
                    name = option.name
                    value = option.value
                    order = option.order
                    icon = option.icon
                    color = option.color
                }
            }?.toMutableList() ?: mutableListOf()
        }
        session.save(unit)

        DataUnitCreatedEvent(unit.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun update(command: DataUnitUpdateCommand): DataUnitUpdatedEvent = sessionFactory.transaction { session, _ ->
        val unit = session.load(DataUnitEntity::class.java, command.id as String, 1)
            ?: throw NotFoundException("DataUnit", command.id)

        val existingOptions = unit.options.associateBy(DataUnitOptionEntity::id)
        session.removeSeveredRelations(
            DataUnitEntity.LABEL, command.id, DataUnitEntity.HAS_OPTION, DataUnitOptionEntity.LABEL,
            existingOptions.keys, command.options?.mapNotNull { it.id }?.toSet() ?: emptySet()
        )

        unit.apply {
            name = command.name
            description = command.description
            notation = command.notation
            options = command.options?.map { option ->
                val optionToMutate = existingOptions[option.id]
                    ?: DataUnitOptionEntity().apply { id = UUID.randomUUID().toString() }
                optionToMutate.apply {
                    identifier = option.identifier
                    name = option.name
                    value = option.value
                    order = option.order
                    icon = option.icon
                    color = option.color
                }
            }?.toMutableList() ?: mutableListOf()
        }.also(session::save)

        DataUnitUpdatedEvent(unit.id)
            .also(applicationEventPublisher::publishEvent)
    }
}

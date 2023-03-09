package cccev.s2.unit.api

import cccev.s2.unit.api.entity.DataUnitAutomateExecutor
import cccev.s2.unit.domain.DataUnitAggregate
import cccev.s2.unit.domain.DataUnitState
import cccev.s2.unit.domain.command.DataUnitCreateCommand
import cccev.s2.unit.domain.command.DataUnitCreatedEvent
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DataUnitAggregateService(
    private val automate: DataUnitAutomateExecutor
): DataUnitAggregate {
    override suspend fun create(command: DataUnitCreateCommand) = automate.init(command) {
        DataUnitCreatedEvent(
            id = UUID.randomUUID().toString(),
            name = command.name,
            description = command.description,
            type = command.type,
            notation = command.notation,
            status = DataUnitState.EXISTS
        )
    }
}

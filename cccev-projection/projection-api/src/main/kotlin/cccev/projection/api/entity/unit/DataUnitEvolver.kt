package cccev.projection.api.entity.unit

import cccev.projection.api.entity.unit.DataUnitRepository
import cccev.s2.unit.domain.DataUnitEvent
import cccev.s2.unit.domain.command.DataUnitCreatedEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import s2.sourcing.dsl.view.View

@Service
class DataUnitEvolver(
	private val dataUnitRepository: DataUnitRepository,
): View<DataUnitEvent, DataUnitEntity> {

	override suspend fun evolve(
		event: DataUnitEvent, model: DataUnitEntity?
	): DataUnitEntity? = when (event) {
		is DataUnitCreatedEvent -> created(event)
		else -> TODO()
	}

	private suspend fun created(event: DataUnitCreatedEvent): DataUnitEntity {
		return DataUnitEntity().apply {
			id = event.id
			name = event.name
			description = event.description
			notation = event.notation
			type = event.type
			status = event.status
		}
	}
}

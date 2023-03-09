package cccev.projection.api.entity.concept

import cccev.projection.api.entity.unit.DataUnitRepository
import cccev.s2.concept.domain.InformationConceptEvent
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import s2.sourcing.dsl.view.View

@Service
class InformationConceptEvolver(
	private val dataUnitRepository: DataUnitRepository,
	private val informationConceptRepository: InformationConceptRepository,
): View<InformationConceptEvent, InformationConceptEntity> {

	override suspend fun evolve(
		event: InformationConceptEvent, model: InformationConceptEntity?
	): InformationConceptEntity? = when (event) {
		is InformationConceptCreatedEvent -> created(event)
		else -> TODO()
	}

	private suspend fun created(event: InformationConceptCreatedEvent): InformationConceptEntity {
		val concepts = informationConceptRepository.findByIdIn(event.dependsOn).collectList().awaitSingle()
		val unit = dataUnitRepository.findById(event.hasUnit).awaitSingle()

		return InformationConceptEntity().apply {
			id = event.id
			name = event.name
			hasUnit = unit
			description = event.description
			expressionOfExpectedValue = event.expressionOfExpectedValue
			dependsOn = concepts
			status = event.status
		}
	}
}

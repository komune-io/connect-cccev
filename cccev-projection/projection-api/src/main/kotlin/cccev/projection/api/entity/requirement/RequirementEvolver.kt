package cccev.projection.api.entity.requirement

import cccev.projection.api.entity.concept.InformationConceptRepository
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import s2.sourcing.dsl.view.View

@Service
class RequirementEvolver(
	private val evidenceTypeListRepository: EvidenceTypeListRepository,
	private val informationConceptRepository: InformationConceptRepository,
	private val requirementRepository: RequirementRepository
): View<RequirementEvent, RequirementEntity> {

	override suspend fun evolve(event: RequirementEvent, model: RequirementEntity?): RequirementEntity? = when (event) {
		is RequirementCreatedEvent -> created(event)
		is RequirementUpdatedEvent -> update(model, event)
		else -> TODO()
	}

	private suspend fun created(event: RequirementCreatedEvent): RequirementEntity {
		val subRequirements = requirementRepository.findByIdentifierIn(event.hasRequirement).collectList().awaitSingle()
		val relatedRequirements = requirementRepository.findByIdentifierIn(event.hasQualifiedRelation.orEmpty()).collectList().awaitSingle()
		val concepts = informationConceptRepository.findByIdIn(event.hasConcept).collectList().awaitSingle()
		val evidenceTypeLists = evidenceTypeListRepository.findByIdIn(event.hasEvidenceTypeList).collectList().awaitSingle()

		return RequirementEntity().apply {
			id = event.id
			identifier = event.identifier
			kind = event.kind
			name = event.name
			description = event.description
			type = null // TODO
			isDerivedFrom = emptyList() // TODO
			hasRequirement = subRequirements
			hasQualifiedRelation = relatedRequirements
			hasConcept = concepts
			hasEvidenceTypeList = evidenceTypeLists
			status = event.status
		}
	}

	private fun update(entity: RequirementEntity?, event: RequirementUpdatedEvent): RequirementEntity? = entity?.apply {
		name = event.name
		description = event.description
	}
}

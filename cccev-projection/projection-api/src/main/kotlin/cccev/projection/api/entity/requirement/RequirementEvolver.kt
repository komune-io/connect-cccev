package cccev.projection.api.entity.requirement

import cccev.projection.api.entity.concept.InformationConceptRepository
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.command.RequirementAddedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementAddedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementAddedRequirementsEvent
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementRemovedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedRequirementsEvent
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
		is RequirementCreatedEvent -> create(event)
		is RequirementUpdatedEvent -> model?.update(event)
		is RequirementAddedRequirementsEvent -> model?.addRequirements(event)
		is RequirementRemovedRequirementsEvent -> model?.removeRequirements(event)
		is RequirementAddedConceptsEvent -> model?.addConcepts(event)
		is RequirementRemovedConceptsEvent -> model?.removeConcepts(event)
		is RequirementAddedEvidenceTypeListsEvent -> model?.addEvidenceTypeLists(event)
		is RequirementRemovedEvidenceTypeListsEvent -> model?.removeEvidenceTypeLists(event)
		else -> TODO()
	}

	private suspend fun create(event: RequirementCreatedEvent): RequirementEntity {
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

	private fun RequirementEntity.update(event: RequirementUpdatedEvent) = apply {
		name = event.name
		description = event.description
	}

	private suspend fun RequirementEntity.addRequirements(event: RequirementAddedRequirementsEvent) = apply {
		hasRequirement += requirementRepository.findByIdentifierIn(event.requirementIds).collectList().awaitSingle()
	}

	private suspend fun RequirementEntity.removeRequirements(event: RequirementRemovedRequirementsEvent) = apply {
		hasRequirement.removeIf { it.identifier in event.requirementIds }
	}

	private suspend fun RequirementEntity.addConcepts(event: RequirementAddedConceptsEvent) = apply {
		hasConcept += informationConceptRepository.findByIdIn(event.conceptIds).collectList().awaitSingle()
	}

	private suspend fun RequirementEntity.removeConcepts(event: RequirementRemovedConceptsEvent) = apply {
		hasConcept.removeIf { it.id in event.conceptIds }
	}

	private suspend fun RequirementEntity.addEvidenceTypeLists(event: RequirementAddedEvidenceTypeListsEvent) = apply {
		hasEvidenceTypeList += evidenceTypeListRepository.findByIdIn(event.evidenceTypeListIds).collectList().awaitSingle()
	}

	private suspend fun RequirementEntity.removeEvidenceTypeLists(event: RequirementRemovedEvidenceTypeListsEvent) = apply {
		hasEvidenceTypeList.removeIf { it.id in event.evidenceTypeListIds }
	}
}

package cccev.s2.requirement.api.view

import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import cccev.s2.requirement.domain.model.Requirement
import cccev.s2.requirement.domain.model.RequirementKind
import s2.sourcing.dsl.view.View

class RequirementView: View<RequirementEvent, Requirement> {

	override suspend fun evolve(event: RequirementEvent, model: Requirement?): Requirement? = when(event) {
		is RequirementCreatedEvent -> created(event)
		is RequirementUpdatedEvent -> update(model, event)
		else -> TODO()
	}

	private fun update(entity: Requirement?, event: RequirementUpdatedEvent): Requirement? = entity?.let { current ->
		TODO()
	}


	private fun created(event: RequirementCreatedEvent): Requirement {
		return Requirement(
			id = event.requirement.id,
			identifier = event.requirement.identifier,
			name = event.requirement.name,
			description = event.requirement.description,
			state = RequirementState.CREATED,
			isRequirementOf = emptyList(),
			kind = RequirementKind.INFORMATION,
			hasQualifiedRelation = emptyList(),
			hasRequirement = emptyList(),
			hasConcept = emptyList(),
			hasEvidenceTypeList = emptyList()
		)
	}
}

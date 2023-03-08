package cccev.s2.requirement.api

import cccev.s2.requirement.api.config.RequirementAutomateExecutor
import cccev.s2.requirement.domain.RequirementAggregate
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdateCommand
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import cccev.s2.requirement.domain.model.Requirement
import java.util.UUID
import org.springframework.stereotype.Service
import s2.sourcing.dsl.Decide

@Service
class RequirementAggregateService(
    private val automate: RequirementAutomateExecutor
): RequirementAggregate {

    override fun create() =
        automate.init<RequirementCreatedEvent, RequirementCreateCommand> { command ->
        val entity = Requirement(
            identifier = command.identifier,
            kind = command.kind,
            name = command.name,
            description = command.description,
            hasRequirement = command.hasRequirement,
            hasConcept = command.hasConcept,
            hasEvidenceTypeList = command.hasEvidenceTypeList,
            isRequirementOf = command.isRequirementOf.orEmpty(),
            hasQualifiedRelation = command.hasQualifiedRelation.orEmpty(),
            id = command.identifier ?: UUID.randomUUID().toString(),
            state = RequirementState.CREATED
        )
        RequirementCreatedEvent(entity)
    }

    override fun update(): Decide<RequirementUpdateCommand, RequirementUpdatedEvent> = automate.decide { command, entity ->
        RequirementUpdatedEvent(
            command.id,
            command.name,
            command.description
        )
    }
}

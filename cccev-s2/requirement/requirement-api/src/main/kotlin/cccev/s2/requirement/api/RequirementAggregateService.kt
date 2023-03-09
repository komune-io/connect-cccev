package cccev.s2.requirement.api

import cccev.s2.requirement.api.entity.RequirementAutomateExecutor
import cccev.s2.requirement.domain.RequirementAggregate
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdateCommand
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class RequirementAggregateService(
    private val automate: RequirementAutomateExecutor
): RequirementAggregate {

    override suspend fun create(command: RequirementCreateCommand) = automate.init(command) {
        RequirementCreatedEvent(
            id = command.identifier ?: UUID.randomUUID().toString(),
            identifier = command.identifier,
            kind = command.kind,
            name = command.name,
            description = command.description,
            hasRequirement = command.hasRequirement,
            hasConcept = command.hasConcept,
            hasEvidenceTypeList = command.hasEvidenceTypeList,
            hasQualifiedRelation = command.hasQualifiedRelation.orEmpty(),
            status = RequirementState.CREATED
        )
    }

    override suspend fun update(command: RequirementUpdateCommand) = automate.transition(command) {
        RequirementUpdatedEvent(
            id = command.id,
            name = command.name,
            description = command.description
        )
    }
}

package cccev.f2.requirement.api.service

import cccev.f2.requirement.domain.command.ConstraintCreateCommandDTOBase
import cccev.f2.requirement.domain.command.CriterionCreateCommandDTOBase
import cccev.f2.requirement.domain.command.InformationRequirementCreateCommandDTOBase
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdateCommand
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import cccev.s2.requirement.domain.model.RequirementKind
import f2.dsl.fnc.invokeWith
import org.springframework.stereotype.Service

@Service
class RequirementF2AggregateService(
    private val requirementAggregateService: RequirementAggregateService
) {
    suspend fun create(command: ConstraintCreateCommandDTOBase): RequirementCreatedEvent {
        return RequirementCreateCommand(
            identifier = command.identifier,
            kind = RequirementKind.CONSTRAINT,
            name = command.name,
            description = command.description,
            hasRequirement = command.hasRequirement,
            hasConcept = command.hasConcept,
            hasEvidenceTypeList = command.hasEvidenceTypeList,
            hasQualifiedRelation = command.hasQualifiedRelation,
        ).let { requirementAggregateService.create(it) }
    }

    suspend fun create(command: CriterionCreateCommandDTOBase): RequirementCreatedEvent {
        return RequirementCreateCommand(
            identifier = command.identifier,
            kind = RequirementKind.CRITERION,
            name = command.name,
            description = command.description,
            hasRequirement = command.hasRequirement,
            hasConcept = command.hasConcept,
            hasEvidenceTypeList = command.hasEvidenceTypeList,
            hasQualifiedRelation = command.hasQualifiedRelation,
        ).let { requirementAggregateService.create(it) }
    }

    suspend fun create(command: InformationRequirementCreateCommandDTOBase): RequirementCreatedEvent {
        return RequirementCreateCommand(
            identifier = command.identifier,
            kind = RequirementKind.INFORMATION,
            name = command.name,
            description = command.description,
            hasRequirement = command.hasRequirement,
            hasConcept = command.hasConcept,
            hasEvidenceTypeList = command.hasEvidenceTypeList,
            hasQualifiedRelation = command.hasQualifiedRelation,
        ).let { requirementAggregateService.create(it) }
    }

    suspend fun update(command: RequirementUpdateCommand): RequirementUpdatedEvent {
        return requirementAggregateService.update(command)
    }
}

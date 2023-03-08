package cccev.s2.requirement.domain

import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementUpdateCommand
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import s2.sourcing.dsl.Decide

interface RequirementAggregate {
	fun create(): Decide<RequirementCreateCommand, RequirementCreatedEvent>
	fun update(): Decide<RequirementUpdateCommand, RequirementUpdatedEvent>
}

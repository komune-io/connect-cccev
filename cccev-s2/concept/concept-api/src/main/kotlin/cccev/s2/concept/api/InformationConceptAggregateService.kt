package cccev.s2.concept.api

import cccev.s2.concept.api.entity.InformationConceptAutomateExecutor
import cccev.s2.concept.domain.InformationConceptAggregate
import cccev.s2.concept.domain.InformationConceptState
import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class InformationConceptAggregateService(
    private val automate: InformationConceptAutomateExecutor
): InformationConceptAggregate {
    override suspend fun create(command: InformationConceptCreateCommand) = automate.init(command) {
        InformationConceptCreatedEvent(
            id = UUID.randomUUID().toString(),
            status = InformationConceptState.EXISTS,
            name = command.name,
            hasUnit = command.hasUnit,
            description = command.description,
            expressionOfExpectedValue = command.expressionOfExpectedValue,
            dependsOn = command.dependsOn,
        )
    }
}

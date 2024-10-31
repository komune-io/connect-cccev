package cccev.core.concept

import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.unit.entity.DataUnitEntity
import cccev.f2.concept.command.InformationConceptCreateCommand
import cccev.f2.concept.command.InformationConceptCreatedEvent
import cccev.f2.concept.command.InformationConceptUpdateCommand
import cccev.f2.concept.command.InformationConceptUpdatedEvent
import cccev.infra.neo4j.findSafeShallowAllById
import cccev.infra.neo4j.findSafeShallowById
import cccev.infra.neo4j.removeSeveredRelations
import cccev.infra.neo4j.transaction
import f2.spring.exception.NotFoundException
import java.util.UUID
import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class InformationConceptAggregateService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val sessionFactory: SessionFactory
) {
    suspend fun create(command: InformationConceptCreateCommand) = sessionFactory.transaction { session, _ ->
        val dependencies = session.findSafeShallowAllById<InformationConceptEntity>(command.dependsOn, InformationConceptEntity.LABEL)
        val unit = session.findSafeShallowById<DataUnitEntity>(command.hasUnit, DataUnitEntity.LABEL)
        val concept = InformationConceptEntity().apply {
            id = UUID.randomUUID().toString()
            identifier = (command.identifier ?: id.replace(Regex("[^a-zA-Z0-9]"), "_"))
            name = command.name
            this.unit = unit
            description = command.description
            expressionOfExpectedValue = command.expressionOfExpectedValue
            this.dependencies = dependencies.toMutableList()
        }.also(session::save)

        InformationConceptCreatedEvent(concept.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun update(command: InformationConceptUpdateCommand) = sessionFactory.transaction { session, _ ->
        val concept = session.load(InformationConceptEntity::class.java, command.id as String, 1)
            ?: throw NotFoundException("InformationConcept", command.id)

        val dependencies = session.findSafeShallowAllById<InformationConceptEntity>(command.dependsOn, InformationConceptEntity.LABEL)

        session.removeSeveredRelations(
            InformationConceptEntity.LABEL, command.id, InformationConceptEntity.DEPENDS_ON, InformationConceptEntity.LABEL,
            concept.dependencies.map { it.id }, command.dependsOn.toSet()
        )

        concept.apply {
            name = command.name
            description = command.description
            expressionOfExpectedValue = command.expressionOfExpectedValue
            this.dependencies = dependencies.toMutableList()
        }.also(session::save)

        InformationConceptUpdatedEvent(concept.id)
            .also(applicationEventPublisher::publishEvent)
    }
}

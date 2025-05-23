package cccev.core.requirement

import cccev.commons.utils.mapAsync
import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.core.requirement.entity.RequirementEntity
import cccev.f2.requirement.command.RequirementAddConceptsCommand
import cccev.f2.requirement.command.RequirementAddEvidenceTypesCommand
import cccev.f2.requirement.command.RequirementAddRequirementsCommand
import cccev.f2.requirement.command.RequirementAddedConceptsEvent
import cccev.f2.requirement.command.RequirementAddedEvidenceTypesEvent
import cccev.f2.requirement.command.RequirementAddedRequirementsEvent
import cccev.f2.requirement.command.RequirementCreateCommand
import cccev.f2.requirement.command.RequirementCreatedEvent
import cccev.f2.requirement.command.RequirementRemoveConceptsCommand
import cccev.f2.requirement.command.RequirementRemoveEvidenceTypesCommand
import cccev.f2.requirement.command.RequirementRemoveRequirementsCommand
import cccev.f2.requirement.command.RequirementRemovedConceptsEvent
import cccev.f2.requirement.command.RequirementRemovedEvidenceTypesEvent
import cccev.f2.requirement.command.RequirementRemovedRequirementsEvent
import cccev.f2.requirement.command.RequirementUpdateCommand
import cccev.f2.requirement.command.RequirementUpdatedEvent
import cccev.infra.neo4j.findSafeShallowAllById
import cccev.infra.neo4j.removeRelation
import cccev.infra.neo4j.removeSeveredRelations
import cccev.infra.neo4j.transaction
import f2.spring.exception.NotFoundException
import java.util.UUID
import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class RequirementAggregateService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val sessionFactory: SessionFactory
) {
    suspend fun create(command: RequirementCreateCommand): RequirementCreatedEvent = sessionFactory.transaction { session, _ ->
        val subRequirements = session.findSafeShallowAllById<RequirementEntity>(command.subRequirementIds.orEmpty(), "Requirement")

        val conceptIds = command.conceptIds.orEmpty().toSet() + command.enablingConditionDependencies.orEmpty() + command.validatingConditionDependencies.orEmpty()
        val concepts = session.findSafeShallowAllById<InformationConceptEntity>(conceptIds, "InformationConcept")
            .associateBy(InformationConceptEntity::id)

        val evidenceTypes = session.findSafeShallowAllById<EvidenceTypeEntity>(command.evidenceTypeListIds.orEmpty(), "EvidenceType")

        val requirement = RequirementEntity().also { requirement ->
            requirement.id = UUID.randomUUID().toString()
            requirement.identifier = command.identifier ?: requirement.id
            requirement.kind = command.kind
            requirement.name = command.name
            requirement.description = command.description
            requirement.type = command.type
            requirement.subRequirements = subRequirements.toMutableList()
            requirement.concepts = command.conceptIds?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            requirement.evidenceTypes = evidenceTypes.toMutableList()
            requirement.enablingCondition = command.enablingCondition
            requirement.enablingConditionDependencies = command.enablingConditionDependencies?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            requirement.required = command.required
            requirement.validatingCondition = command.validatingCondition
            requirement.validatingConditionDependencies = command.validatingConditionDependencies?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            requirement.evidenceValidatingCondition = command.evidenceValidatingCondition
            requirement.order = command.order
            requirement.properties = command.properties
        }
        session.save(requirement)

        RequirementCreatedEvent(requirement.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun update(command: RequirementUpdateCommand): RequirementUpdatedEvent = sessionFactory.transaction { session, _ ->
        val requirement = session.load(RequirementEntity::class.java, command.id as String, 1)
            ?: throw NotFoundException("Requirement", command.id)

        val subRequirements = session.findSafeShallowAllById<RequirementEntity>(command.subRequirementIds.orEmpty(), "Requirement")
        val conceptIds: List<String> = command.conceptIds.orEmpty() + command.enablingConditionDependencies.orEmpty() + command.validatingConditionDependencies.orEmpty()

        val concepts = session.findSafeShallowAllById<InformationConceptEntity>(conceptIds, "InformationConcept")
            .associateBy(InformationConceptEntity::id)
        val evidenceTypes = session.findSafeShallowAllById<EvidenceTypeEntity>(command.evidenceTypeIds.orEmpty(), "EvidenceType")

        session.removeSeveredRelations(
            RequirementEntity.LABEL, command.id, RequirementEntity.HAS_REQUIREMENT, RequirementEntity.LABEL,
            requirement.subRequirements.map { it.id }, command.subRequirementIds?.toSet().orEmpty()
        )
        session.removeSeveredRelations(
            RequirementEntity.LABEL, command.id, RequirementEntity.HAS_CONCEPT, InformationConceptEntity.LABEL,
            requirement.concepts.map { it.id }, command.conceptIds?.toSet().orEmpty()
        )
        session.removeSeveredRelations(
            RequirementEntity.LABEL, command.id, RequirementEntity.ENABLING_DEPENDS_ON, InformationConceptEntity.LABEL,
            requirement.enablingConditionDependencies.map { it.id }, command.enablingConditionDependencies?.toSet().orEmpty()
        )
        session.removeSeveredRelations(
            RequirementEntity.LABEL, command.id, RequirementEntity.VALIDATION_DEPENDS_ON, InformationConceptEntity.LABEL,
            requirement.validatingConditionDependencies.map { it.id }, command.validatingConditionDependencies?.toSet().orEmpty()
        )
        session.removeSeveredRelations(
            RequirementEntity.LABEL, command.id, RequirementEntity.HAS_EVIDENCE_TYPE, EvidenceTypeEntity.LABEL,
            requirement.evidenceTypes.map { it.id }, command.evidenceTypeIds?.toSet().orEmpty()

        )

        requirement.also { r ->
            r.name = command.name
            r.description = command.description
            r.type = command.type
            r.concepts = command.conceptIds?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            r.evidenceTypes = evidenceTypes.toMutableList()
            r.subRequirements = subRequirements.toMutableList()
            r.enablingCondition = command.enablingCondition
            r.enablingConditionDependencies = command.enablingConditionDependencies?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            r.required = command.required
            r.validatingCondition = command.validatingCondition
            r.validatingConditionDependencies = command.validatingConditionDependencies?.mapNotNull { concepts[it] }.orEmpty().toMutableList()
            r.evidenceValidatingCondition = command.evidenceValidatingCondition
            r.order = command.order
            r.properties = command.properties
        }
        session.save(requirement)

        RequirementUpdatedEvent(command.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun addRequirements(command: RequirementAddRequirementsCommand) = sessionFactory.transaction { session, _ ->
        val requirement = session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        val subRequirements = session.findSafeShallowAllById<RequirementEntity>(command.requirementIds, "Requirement")

        requirement.subRequirements.addAll(subRequirements)
        session.save(requirement)

        RequirementAddedRequirementsEvent(command.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun removeRequirements(command: RequirementRemoveRequirementsCommand) = sessionFactory.transaction { session, _ ->
        session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        command.requirementIds.mapAsync { requirementId ->
            session.removeRelation(RequirementEntity.LABEL, command.id, RequirementEntity.HAS_REQUIREMENT, RequirementEntity.LABEL, requirementId)
        }

        RequirementRemovedRequirementsEvent(command.id)
    }

    suspend fun addConcepts(command: RequirementAddConceptsCommand) = sessionFactory.transaction { session, _ ->
        val requirement = session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        val concepts = session.findSafeShallowAllById<InformationConceptEntity>(command.conceptIds, "InformationConcept")

        requirement.concepts.addAll(concepts)
        session.save(requirement)

        RequirementAddedConceptsEvent(command.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun removeConcepts(command: RequirementRemoveConceptsCommand) = sessionFactory.transaction { session, _ ->
        session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        command.conceptIds.mapAsync { conceptId ->
            session.removeRelation(RequirementEntity.LABEL, command.id, RequirementEntity.HAS_CONCEPT, InformationConceptEntity.LABEL, conceptId)
        }

        RequirementRemovedConceptsEvent(command.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun addEvidenceTypes(command: RequirementAddEvidenceTypesCommand) = sessionFactory.transaction { session, _ ->
        val requirement = session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        val evidenceTypes = session.findSafeShallowAllById<EvidenceTypeEntity>(command.evidenceTypeIds, "EvidenceType")

        requirement.evidenceTypes.addAll(evidenceTypes)
        requirement.evidenceValidatingCondition = command.evidenceValidatingCondition
        session.save(requirement)

        RequirementAddedEvidenceTypesEvent(command.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun removeEvidenceTypes(command: RequirementRemoveEvidenceTypesCommand) = sessionFactory.transaction { session, _ ->
        val requirement = session.load(RequirementEntity::class.java, command.id as String, 0)
            ?: throw NotFoundException("Requirement", command.id)

        command.evidenceTypeIds.mapAsync { evidenceTypeId ->
            session.removeRelation(RequirementEntity.LABEL, command.id, RequirementEntity.HAS_EVIDENCE_TYPE, EvidenceTypeEntity.LABEL, evidenceTypeId)
        }
        requirement.evidenceValidatingCondition = command.evidenceValidatingCondition
        session.save(requirement)


        RequirementRemovedEvidenceTypesEvent(command.id, command.evidenceTypeIds)
            .also(applicationEventPublisher::publishEvent)
    }
}

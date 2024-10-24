package cccev.core.certification

import cccev.core.certification.entity.Certification
import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.RequirementCertification
import cccev.core.certification.entity.isFulfilled
import cccev.core.certification.service.CertificationValuesFillerService
import cccev.core.requirement.RequirementRepository2
import cccev.f2.certification.domain.command.CertificationAddRequirementsCommand
import cccev.f2.certification.domain.command.CertificationAddedRequirementsEvent
import cccev.f2.certification.domain.command.CertificationCreateCommand
import cccev.f2.certification.domain.command.CertificationCreatedEvent
import cccev.f2.certification.domain.command.CertificationFillValuesCommand
import cccev.f2.certification.domain.command.CertificationFilledValuesEvent
import cccev.f2.certification.domain.command.CertificationRemoveRequirementsCommand
import cccev.f2.certification.domain.command.CertificationRemovedRequirementsEvent
import cccev.infra.neo4j.session
import cccev.projection.api.entity.requirement.RequirementEntity
import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service("CertificationAggregateService2")
class CertificationAggregateService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val certificationRepository: CertificationRepository,
    private val certificationValuesFillerService: CertificationValuesFillerService,
    private val requirementRepository: RequirementRepository2,
    private val sessionFactory: SessionFactory
) {
    suspend fun create(command: CertificationCreateCommand): CertificationCreatedEvent = sessionFactory.session { session ->
        command.id?.let { id ->
            val existingCertification = session.load(Certification::class.java, id as String, 0)
            if (existingCertification != null) {
                throw ConflictException("Certification", "id", id)
            }
        }

        val requirements = command.requirementIdentifiers.map { requirementIdentifier ->
            requirementRepository.loadRequirementOnlyGraph(requirementIdentifier)
                ?: throw NotFoundException("Requirement", requirementIdentifier)
        }

        val certification = Certification().apply {
            id = command.id ?: UUID.randomUUID().toString()
            requirementCertifications = requirements.map { it.toEmptyCertification() }.toMutableList()
        }

        certificationRepository.save(certification)

        CertificationCreatedEvent(certification.id)
            .also(applicationEventPublisher::publishEvent)
    }

    suspend fun fillValues(command: CertificationFillValuesCommand): CertificationFilledValuesEvent {
        val context = CertificationValuesFillerService.Context(
            certificationId = command.id,
            rootRequirementCertificationId = command.rootRequirementCertificationId
        )
        certificationValuesFillerService.fillValues(command.values, context)

        return CertificationFilledValuesEvent(
            id = command.id,
            rootRequirementCertificationId = command.rootRequirementCertificationId
        )
    }

    suspend fun addRequirements(
        command: CertificationAddRequirementsCommand
    ): CertificationAddedRequirementsEvent = sessionFactory.session { session ->
        val requirements = command.requirementIdentifiers.map { requirementIdentifier ->
            requirementRepository.loadRequirementOnlyGraph(requirementIdentifier)
                ?: throw NotFoundException("Requirement", requirementIdentifier)
        }

        val requirementCertifications = requirements.map { it.toEmptyCertification() }

        if (command.parentId == null) {
            val certification = session.load(Certification::class.java, command.id as String, 0)
                ?: throw NotFoundException("Certification", command.id)
            certification.requirementCertifications.addAll(requirementCertifications)
            certificationRepository.save(certification)
        } else {
            if (!certificationRepository.hasRequirementCertification(command.id, command.parentId!!)) {
                throw NotFoundException("RequirementCertification [${command.parentId}] in Certification", command.id)
            }

            val parentRequirementCertification = session.load(RequirementCertification::class.java, command.parentId as String, 0)
                ?: throw NotFoundException("RequirementCertification", command.parentId!!)
            parentRequirementCertification.subCertifications.addAll(requirementCertifications)
            certificationRepository.save(parentRequirementCertification)
        }

        CertificationAddedRequirementsEvent(
            id = command.id,
            parentId = command.parentId,
            requirementCertificationIds = requirementCertifications.map { it.id }
        ).also(applicationEventPublisher::publishEvent)
    }

    suspend fun removeRequirements(command: CertificationRemoveRequirementsCommand): CertificationRemovedRequirementsEvent {
        TODO()
    }

    private suspend fun RequirementEntity.toEmptyCertification(): RequirementCertification = RequirementCertification().apply {
        id = UUID.randomUUID().toString()
        requirement = this@toEmptyCertification
        hasRequirementTmp.forEach { requirement ->
            subCertifications.add(requirement.toEmptyCertification())
        }
        isEnabled = enablingCondition == null
        isValidated = validatingCondition == null
        hasAllValues = !requirementRepository.hasAnyConcept(identifier!!)
        isFulfilled = isFulfilled()
    }
}

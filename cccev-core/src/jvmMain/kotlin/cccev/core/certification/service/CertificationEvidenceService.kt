package cccev.core.certification.service

import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.EvidenceEntity
import cccev.core.certification.entity.RequirementCertificationEntity
import cccev.core.certification.entity.isFulfilled
import cccev.core.evidencetype.entity.EvidenceTypeRepository
import cccev.dsl.model.EvidenceId
import cccev.f2.certification.command.CertificationAddEvidenceCommand
import cccev.infra.neo4j.session
import cccev.infra.neo4j.transaction
import f2.spring.exception.NotFoundException
import io.komune.fs.s2.file.domain.model.FilePath
import java.util.UUID
import org.neo4j.ogm.session.SessionFactory
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

@Service
class CertificationEvidenceService(
    private val certificationRepository: CertificationRepository,
    private val evidenceTypeRepository: EvidenceTypeRepository,
    private val sessionFactory: SessionFactory
) {
    companion object {
        val spelParser = SpelExpressionParser()
    }

    /**
     * Add the given evidence to the relevant certifications,
     * then update the fulfillment indicators of said certifications,
     * then link the evidence to the values it supports within the certifications.
     */
    suspend fun addEvidence(
        command: CertificationAddEvidenceCommand, filePath: FilePath
    ): EvidenceId = sessionFactory.transaction { _, _ ->
        certificationRepository.findById(command.id)
            ?: throw NotFoundException("Certification", command.id)

        val evidenceType = evidenceTypeRepository.findById(command.evidenceTypeId)
            ?: throw NotFoundException("EvidenceType", command.evidenceTypeId)

        val requirementCertifications = certificationRepository.findRequirementCertificationsLinkedToEvidenceType(
            command.id, command.rootRequirementCertificationId, command.evidenceTypeId
        )

        val evidence = EvidenceEntity().also { evidence ->
            evidence.id = UUID.randomUUID().toString()
            evidence.evidenceType = evidenceType
            evidence.file = filePath
        }

        requirementCertifications.forEach { requirementCertification ->
            requirementCertification.addEvidence(evidence)
        }

        evidence.linkWithValuesOf(requirementCertifications)

        evidence.id
    }

    private suspend fun RequirementCertificationEntity.addEvidence(evidence: EvidenceEntity) = sessionFactory.session { session ->
        val existingEvidence = evidences
            .firstOrNull { it.evidenceType.id == evidence.evidenceType.id }

        if (existingEvidence != null) {
            existingEvidence.file = evidence.file
            session.save(existingEvidence, 0)
        } else {
            evidences.add(evidence)
            session.save( this, 2)
        }

        updateFulfillment()
    }

    private suspend fun RequirementCertificationEntity.updateFulfillment() {
        var changed: Boolean

        val evaluationResult = requirement.evidenceValidatingCondition?.let { expression ->
            val expressionContext = StandardEvaluationContext().apply {
                evidences.forEach { setVariable(it.evidenceType.id, it.file) }
            }

            spelParser.parseExpression(expression)
                .getValue(expressionContext, Boolean::class.java)
                ?: false
        } ?: true

        areEvidencesProvided = evaluationResult
            .also { changed = it != areEvidencesProvided }

        isFulfilled = isFulfilled()
            .also { changed = changed || it != isFulfilled }

        if (changed) {
            certificationRepository.save(this)
            certificationRepository.findParentsOf(id)
                .forEach { parent -> parent.updateFulfillment() }
        }
    }

    private suspend fun EvidenceEntity.linkWithValuesOf(
        requirementCertifications: Collection<RequirementCertificationEntity>
    ) = sessionFactory.session { session ->
        val supportedValues = certificationRepository.findSupportedValuesSupportedByEvidenceType(
            evidenceTypeId = evidenceType.id,
            requirementCertificationIds = requirementCertifications.map { it.id }
        )

        supportedValues.forEach { supportedValue ->
            if (supportedValue.evidences.none { it.evidenceType.id == evidenceType.id }) {
                supportedValue.evidences.add(this)
                session.save(supportedValue, 1)
            }
        }
    }
}

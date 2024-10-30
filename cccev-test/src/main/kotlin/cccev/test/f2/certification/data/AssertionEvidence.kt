package cccev.test.f2.certification.data

import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.Evidence
import cccev.dsl.model.EvidenceId
import cccev.dsl.model.EvidenceTypeId
import io.komune.fs.s2.file.domain.model.FilePath
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.evidence(repository: CertificationRepository) = AssertionEvidence(repository)

class AssertionEvidence(
    private val repository: CertificationRepository
): AssertionApiEntity<Evidence, EvidenceId, AssertionEvidence.EvidenceAssert>() {

    override suspend fun findById(id: EvidenceId) = repository.findEvidenceById(id)
    override suspend fun assertThat(entity: Evidence) = EvidenceAssert(entity)

    inner class EvidenceAssert(
        private val evidence: Evidence
    ) {
        fun hasFields(
            id: EvidenceId = evidence.id,
            filePath: FilePath = evidence.file,
            evidenceTypeId: EvidenceTypeId = evidence.evidenceType.id,
        ) = also {
            Assertions.assertThat(evidence.id).isEqualTo(id)
            Assertions.assertThat(evidence.file).isEqualTo(filePath)
            Assertions.assertThat(evidence.evidenceType.id).isEqualTo(evidenceTypeId)
        }
    }
}

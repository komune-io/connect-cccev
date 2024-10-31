package cccev.test.f2.certification.data

import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.SupportedValueEntity
import cccev.dsl.model.EvidenceId
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.SupportedValueId
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.supportedValue(repository: CertificationRepository) = AssertionSupportedValue(repository)

class AssertionSupportedValue(
    private val repository: CertificationRepository
): AssertionApiEntity<SupportedValueEntity, SupportedValueId, AssertionSupportedValue.SupportedValueAssert>() {

    override suspend fun findById(id: SupportedValueId) = repository.findSupportedValueById(id)
    override suspend fun assertThat(entity: SupportedValueEntity) = SupportedValueAssert(entity)

    inner class SupportedValueAssert(
        private val supportedValue: SupportedValueEntity
    ) {
        fun hasFields(
            id: SupportedValueId = supportedValue.id,
            value: String? = supportedValue.value,
            conceptId: InformationConceptId = supportedValue.concept.id,
            evidenceIds: Collection<EvidenceId> = supportedValue.evidences.map { it.id }
        ) = also {
            Assertions.assertThat(supportedValue.id).isEqualTo(id)
            Assertions.assertThat(supportedValue.value).isEqualTo(value)
            Assertions.assertThat(supportedValue.concept.id).isEqualTo(conceptId)
            Assertions.assertThat(supportedValue.evidences.map { it.id }).containsExactlyInAnyOrderElementsOf(evidenceIds)
        }
    }
}

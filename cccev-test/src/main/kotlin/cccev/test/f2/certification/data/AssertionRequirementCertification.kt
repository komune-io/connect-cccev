package cccev.test.f2.certification.data

import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.RequirementCertification
import cccev.core.certification.model.RequirementCertificationId
import cccev.core.certification.model.SupportedValueId
import cccev.core.requirement.model.RequirementId
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.requirementCertification(repository: CertificationRepository) = AssertionRequirementCertification(repository)

class AssertionRequirementCertification(
    private val repository: CertificationRepository
): AssertionApiEntity<RequirementCertification, RequirementCertificationId, AssertionRequirementCertification.RequirementCertificationAssert>() {

    override suspend fun findById(id: RequirementCertificationId) = repository.findRequirementCertificationById(id)
    override suspend fun assertThat(entity: RequirementCertification) = RequirementCertificationAssert(entity)

    inner class RequirementCertificationAssert(
        private val certification: RequirementCertification
    ) {
        fun hasFields(
            id: RequirementCertificationId = certification.id,
            requirementId: RequirementId = certification.requirement.id,
            subCertificationIds: Collection<RequirementCertificationId> = certification.subCertifications.map { it.id },
            values: Collection<SupportedValueId> = certification.values.map { it.id },
            isEnabled: Boolean = certification.isEnabled,
            isValidated: Boolean = certification.isValidated,
            hasAllValues: Boolean = certification.hasAllValues,
            isFulfilled: Boolean = certification.isFulfilled
        ) = also {
            Assertions.assertThat(certification.id).isEqualTo(id)
            Assertions.assertThat(certification.requirement.id).isEqualTo(requirementId)
            Assertions.assertThat(certification.subCertifications.map { it.id }).containsExactlyInAnyOrderElementsOf(subCertificationIds)
            Assertions.assertThat(certification.values.map { it.id }).containsExactlyInAnyOrderElementsOf(values)
            Assertions.assertThat(certification.isEnabled).isEqualTo(isEnabled)
            Assertions.assertThat(certification.isValidated).isEqualTo(isValidated)
            Assertions.assertThat(certification.hasAllValues).isEqualTo(hasAllValues)
            Assertions.assertThat(certification.isFulfilled).isEqualTo(isFulfilled)
        }
    }
}

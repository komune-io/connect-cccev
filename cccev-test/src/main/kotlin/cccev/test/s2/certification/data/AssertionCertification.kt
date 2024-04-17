//package cccev.test.s2.certification.data
//
//import cccev.projection.api.entity.certification.CertificationEntity
//import cccev.projection.api.entity.certification.CertificationRepositoryOld
//import cccev.projection.api.entity.certification.EvidenceEntity
//import cccev.s2.certification.domain.CertificationState
//import cccev.s2.certification.domain.model.CertificationId
//import cccev.s2.certification.domain.model.CertificationIdentifier
//import cccev.s2.certification.domain.model.EvidenceId
//import cccev.s2.concept.domain.InformationConceptId
//import cccev.s2.evidence.type.domain.EvidenceTypeId
//import cccev.s2.requirement.domain.RequirementId
//import io.komune.fs.s2.file.domain.model.FilePath
//import org.assertj.core.api.Assertions
//import s2.bdd.assertion.AssertionBdd
//import s2.bdd.repository.AssertionCrudEntity
//
//fun AssertionBdd.certification(conceptRepository: CertificationRepositoryOld) = AssertionCertification(conceptRepository)
//
//class AssertionCertification(
//    override val repository: CertificationRepositoryOld
//): AssertionCrudEntity<CertificationEntity, CertificationId, AssertionCertification.CertificationAssert>() {
//
//    override suspend fun assertThat(entity: CertificationEntity) = CertificationAssert(entity)
//
//    inner class CertificationAssert(
//        private val entity: CertificationEntity
//    ) {
//        fun hasFields(
//            id: CertificationId = entity.id,
//            identifier: CertificationIdentifier = entity.identifier,
//            status: CertificationState = entity.status,
//            name: String? = entity.name,
//            description: String? = entity.description,
//            startDate: Long? = entity.startDate,
//            endDate: Long? = entity.endDate,
//            estimatedEndDate: Long? = entity.estimatedEndDate,
//            creator: String? = entity.creator,
//            executor: String? = entity.executor,
//            validator: String? = entity.validator,
//            isPublic: Boolean = entity.isPublic,
//            issuable: Boolean = entity.issuable,
//            verifiable: Boolean = entity.verifiable,
//            verifier: String? = entity.verifier,
//            verificationDate: Long? = entity.verificationDate,
//            requirements: Collection<RequirementId> = entity.requirements.map { it.id },
//            evidences: Collection<EvidenceId> = entity.evidences.map { it.id },
//            supportedValues: Map<InformationConceptId, String?> = entity.supportedValues.associate { it.providesValueFor.id to it.value }
//        ) = also {
//            Assertions.assertThat(entity.id).isEqualTo(id)
//            Assertions.assertThat(entity.identifier).isEqualTo(identifier)
//            Assertions.assertThat(entity.status).isEqualTo(status)
//            Assertions.assertThat(entity.name).isEqualTo(name)
//            Assertions.assertThat(entity.description).isEqualTo(description)
//            Assertions.assertThat(entity.startDate).isEqualTo(startDate)
//            Assertions.assertThat(entity.endDate).isEqualTo(endDate)
//            Assertions.assertThat(entity.estimatedEndDate).isEqualTo(estimatedEndDate)
//            Assertions.assertThat(entity.creator).isEqualTo(creator)
//            Assertions.assertThat(entity.executor).isEqualTo(executor)
//            Assertions.assertThat(entity.validator).isEqualTo(validator)
//            Assertions.assertThat(entity.isPublic).isEqualTo(isPublic)
//            Assertions.assertThat(entity.issuable).isEqualTo(issuable)
//            Assertions.assertThat(entity.verifiable).isEqualTo(verifiable)
//            Assertions.assertThat(entity.verifier).isEqualTo(verifier)
//            Assertions.assertThat(entity.verificationDate).isEqualTo(verificationDate)
//            Assertions.assertThat(entity.requirements.map { it.id }).containsExactlyInAnyOrderElementsOf(requirements)
//            Assertions.assertThat(entity.evidences.map { it.id }).containsExactlyInAnyOrderElementsOf(evidences)
//            Assertions.assertThat(entity.supportedValues.associate { it.providesValueFor.id to it.value })
//                .containsExactlyInAnyOrderEntriesOf(supportedValues)
//        }
//
//        fun hasRequirements(ids: Collection<RequirementId>) {
//            Assertions.assertThat(entity.requirements.map { it.id }).containsAll(ids)
//        }
//
//        fun doesNotHaveRequirements(ids: Collection<RequirementId>) {
//            Assertions.assertThat(entity.requirements.map { it.id }).doesNotContainAnyElementsOf(ids)
//        }
//
//        fun hasEvidences(ids: Collection<EvidenceId>) {
//            Assertions.assertThat(entity.evidences.map { it.id }).containsAll(ids)
//        }
//
//        fun doesNotHaveEvidences(ids: Collection<EvidenceId>) {
//            Assertions.assertThat(entity.evidences.map { it.id }).doesNotContainAnyElementsOf(ids)
//        }
//
//        fun assertThatEvidence(id: EvidenceId): EvidenceAssert {
//            hasEvidences(listOf(id))
//            return EvidenceAssert(entity.evidences.first { it.id == id})
//        }
//
//        fun hasSupportedValues(values: Map<InformationConceptId, String?>) {
//            val certificationValues = entity.supportedValues.associateBy { it.providesValueFor.id }
//            values.forEach { (conceptId, value) ->
//                Assertions.assertThat(certificationValues.keys).contains(conceptId)
//                val supportedValue = certificationValues[conceptId]!!
//                Assertions.assertThat(supportedValue.value).isEqualTo(value)
//            }
//        }
//    }
//
//    inner class EvidenceAssert(
//        private val evidence: EvidenceEntity
//    ) {
//        fun hasFields(
//            id: EvidenceId = evidence.id,
//            name: String = evidence.name,
//            file: FilePath? = evidence.file,
//            url: String? = evidence.url,
//            isConformantTo: List<EvidenceTypeId> = evidence.isConformantTo.map { it.id },
//            supportsConcept: List<InformationConceptId> = evidence.supportsConcept.map { it.id }
//        ) {
//            Assertions.assertThat(evidence.id).isEqualTo(id)
//            Assertions.assertThat(evidence.name).isEqualTo(name)
//            Assertions.assertThat(evidence.file).isEqualTo(file)
//            Assertions.assertThat(evidence.url).isEqualTo(url)
//            Assertions.assertThat(evidence.isConformantTo.map { it.id }).containsExactlyInAnyOrderElementsOf(isConformantTo)
//            Assertions.assertThat(evidence.supportsConcept.map { it.id }).containsExactlyInAnyOrderElementsOf(supportsConcept)
//        }
//    }
//}

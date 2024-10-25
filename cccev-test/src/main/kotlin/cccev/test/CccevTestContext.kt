package cccev.test

import cccev.core.certification.model.CertificationId
import cccev.core.certification.model.EvidenceId
import cccev.core.certification.model.RequirementCertificationId
import cccev.core.certification.model.SupportedValueId
import cccev.core.concept.model.InformationConceptId
import cccev.core.evidencetype.model.EvidenceTypeId
import cccev.core.requirement.model.RequirementId
import cccev.core.unit.model.DataUnitId
import org.springframework.stereotype.Component
import s2.bdd.data.TestContext
import s2.bdd.data.TestContextKey

typealias InformationConceptKey = TestContextKey
typealias EvidenceTypeKey = TestContextKey
typealias RequirementKey = TestContextKey
typealias DataUnitKey = TestContextKey

typealias EvidenceKey = TestContextKey
typealias CertificationKey = TestContextKey
typealias SupportedValueKey = TestContextKey

@Component
class CccevTestContext: TestContext() {

    val conceptIds = testEntities<InformationConceptKey, InformationConceptId>("InformationConcept")
    val evidenceTypeIds = testEntities<EvidenceTypeKey, EvidenceTypeId>("EvidenceType")
    val requirementIds = testEntities<RequirementKey, RequirementId>("Requirement")
    val unitIds = testEntities<DataUnitKey, DataUnitId>("DataUnit")

    val evidenceIds = testEntities<EvidenceKey, EvidenceId>("Evidence")
    val certificationIds = testEntities<CertificationKey, CertificationId>("Request")
    val requirementCertificationIds = testEntities<Pair<CertificationKey, RequirementKey>, RequirementCertificationId>("RequirementCertification")
    val supportedValueIds = testEntities<SupportedValueKey, SupportedValueId>("SupportedValue")

    final var fetched = FetchContext()
        private set

    override fun resetEnv() {
        fetched = FetchContext()
    }

    class FetchContext {
//        lateinit var certification: Certification
    }
}

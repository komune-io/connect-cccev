package cccev.test

import cccev.dsl.model.CertificationId
import cccev.dsl.model.DataUnitId
import cccev.dsl.model.EvidenceId
import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.RequirementCertificationId
import cccev.dsl.model.RequirementId
import cccev.dsl.model.SupportedValueId
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

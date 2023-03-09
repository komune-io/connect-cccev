package cccev.test

import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.unit.domain.DataUnitId
import fixers.bdd.data.TestContext
import fixers.bdd.data.TestContextKey
import org.springframework.stereotype.Component

@Component
class CccevTestContext: TestContext() {
    val conceptIds = testEntities<TestContextKey, InformationConceptId>("InformationConcept")
    val evidenceTypeIds = testEntities<TestContextKey, EvidenceTypeId>("EvidenceType")
    val evidenceTypeListIds = testEntities<TestContextKey, EvidenceTypeListId>("EvidenceTypeList")
    val requirementIds = testEntities<TestContextKey, RequirementId>("Requirement")
    val requirementIdentifiers = testEntities<TestContextKey, RequirementIdentifier>("Requirement with identifier")
    val unitIds = testEntities<TestContextKey, DataUnitId>("DataUnit")

    final var fetched = FetchContext()
        private set

    override fun resetEnv() {
        fetched = FetchContext()
    }

    class FetchContext {
//    lateinit var traces: List<TraceEntity>
    }
}

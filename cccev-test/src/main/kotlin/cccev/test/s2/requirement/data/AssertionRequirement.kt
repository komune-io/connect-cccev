package cccev.test.s2.requirement.data

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.evidence.type.domain.EvidenceTypeListId
import cccev.s2.framework.domain.FrameworkId
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.model.RequirementKind
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionCrudEntity

fun AssertionBdd.requirement(conceptRepository: RequirementRepository) = AssertionRequirement(conceptRepository)

class AssertionRequirement(
    override val repository: RequirementRepository
): AssertionCrudEntity<RequirementEntity, RequirementId, AssertionRequirement.RequirementAssert>() {

    override suspend fun assertThat(entity: RequirementEntity) = RequirementAssert(entity)

    inner class RequirementAssert(
        private val requirement: RequirementEntity
    ) {
        fun hasFields(
            id: RequirementId = requirement.id,
            status: RequirementState = requirement.status,
            kind: RequirementKind = requirement.kind,
            name: String? = requirement.name,
            description: String? = requirement.description,
            type: String? = requirement.type,
            isDerivedFrom: List<FrameworkId> = requirement.isDerivedFrom.map { it.id },
            hasRequirement: List<RequirementId> = requirement.hasRequirement().map { it.id },
            hasConcept: List<InformationConceptId> = requirement.hasConcept.map { it.id },
            hasEvidenceTypeList: List<EvidenceTypeListId> = requirement.hasEvidenceTypeList.map { it.id },
        ) = also {
            Assertions.assertThat(requirement.id).isEqualTo(id)
            Assertions.assertThat(requirement.status).isEqualTo(status)
            Assertions.assertThat(requirement.kind).isEqualTo(kind)
            Assertions.assertThat(requirement.name).isEqualTo(name)
            Assertions.assertThat(requirement.description).isEqualTo(description)
            Assertions.assertThat(requirement.type).isEqualTo(type)
            Assertions.assertThat(requirement.isDerivedFrom.map { it.id }).containsExactlyInAnyOrderElementsOf(isDerivedFrom)
            Assertions.assertThat(requirement.hasRequirement().map { it.id }).containsExactlyInAnyOrderElementsOf(hasRequirement)
            Assertions.assertThat(requirement.hasConcept.map { it.id }).containsExactlyInAnyOrderElementsOf(hasConcept)
            Assertions.assertThat(requirement.hasEvidenceTypeList.map { it.id }).containsExactlyInAnyOrderElementsOf(hasEvidenceTypeList)
        }

        fun hasRequirements(ids: Collection<RequirementId>) {
            Assertions.assertThat(requirement.hasRequirement().map { it.id }).containsAll(ids)
        }

        fun doesNotHaveRequirements(ids: Collection<RequirementId>) {
            Assertions.assertThat(requirement.hasRequirement().map { it.id }).doesNotContainAnyElementsOf(ids)
        }

        fun hasConcepts(ids: Collection<InformationConceptId>) {
            Assertions.assertThat(requirement.hasConcept.map { it.id }).containsAll(ids)
        }

        fun doesNotHaveConcepts(ids: Collection<InformationConceptId>) {
            Assertions.assertThat(requirement.hasConcept.map { it.id }).doesNotContainAnyElementsOf(ids)
        }

        fun hasEvidenceTypeLists(ids: Collection<EvidenceTypeListId>) {
            Assertions.assertThat(requirement.hasEvidenceTypeList.map { it.id }).containsAll(ids)
        }

        fun doesNotHaveEvidenceTypeLists(ids: Collection<EvidenceTypeListId>) {
            Assertions.assertThat(requirement.hasEvidenceTypeList.map { it.id }).doesNotContainAnyElementsOf(ids)
        }
    }
}

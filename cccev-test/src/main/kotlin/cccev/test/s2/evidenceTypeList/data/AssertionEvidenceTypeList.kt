package cccev.test.s2.evidenceTypeList.data

import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEntity
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.EvidenceTypeListState
import cccev.test.AssertionCrudEntity
import fixers.bdd.assertion.AssertionBdd
import org.assertj.core.api.Assertions

fun AssertionBdd.evidenceTypeList(evidenceTypeListRepository: EvidenceTypeListRepository)
    = AssertionEvidenceTypeList(evidenceTypeListRepository)

class AssertionEvidenceTypeList(
    override val repository: EvidenceTypeListRepository
): AssertionCrudEntity<EvidenceTypeListEntity, EvidenceTypeListId, AssertionEvidenceTypeList.EvidenceTypeListAssert>() {

    override suspend fun assertThat(entity: EvidenceTypeListEntity) = EvidenceTypeListAssert(entity)

    inner class EvidenceTypeListAssert(
        private val evidenceTypeList: EvidenceTypeListEntity
    ) {
        fun hasFields(
            id: EvidenceTypeListId = evidenceTypeList.id,
            status: EvidenceTypeListState = evidenceTypeList.status,
            name: String = evidenceTypeList.name,
            description: String = evidenceTypeList.description,
            specifiesEvidenceType: List<EvidenceTypeId> = evidenceTypeList.specifiesEvidenceType.map { it.id },
        ) = also {
            Assertions.assertThat(evidenceTypeList.id).isEqualTo(id)
            Assertions.assertThat(evidenceTypeList.status).isEqualTo(status)
            Assertions.assertThat(evidenceTypeList.name).isEqualTo(name)
            Assertions.assertThat(evidenceTypeList.description).isEqualTo(description)
            Assertions.assertThat(evidenceTypeList.specifiesEvidenceType.map { it.id })
                .containsExactlyInAnyOrderElementsOf(specifiesEvidenceType)
        }
    }
}

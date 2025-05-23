package cccev.test.f2.evidencetype.data

import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.core.evidencetype.entity.EvidenceTypeRepository
import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.InformationConceptId
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.evidenceType(repository: EvidenceTypeRepository) = AssertionEvidenceType(repository)

class AssertionEvidenceType(
    private val repository: EvidenceTypeRepository
): AssertionApiEntity<EvidenceTypeEntity, EvidenceTypeId, AssertionEvidenceType.EvidenceTypeAssert>() {

    override suspend fun findById(id: EvidenceTypeId) = repository.findById(id)
    override suspend fun assertThat(entity: EvidenceTypeEntity) = EvidenceTypeAssert(entity)

    inner class EvidenceTypeAssert(
        private val evidenceType: EvidenceTypeEntity
    ) {
        fun hasFields(
            id: EvidenceTypeId = evidenceType.id,
            name: String = evidenceType.name,
            concepts: Collection<InformationConceptId> = evidenceType.concepts.map { it.id },
        ) = also {
            Assertions.assertThat(evidenceType.id).isEqualTo(id)
            Assertions.assertThat(evidenceType.name).isEqualTo(name)
            Assertions.assertThat(evidenceType.concepts.map { it.id }).containsExactlyInAnyOrderElementsOf(concepts)
        }
    }
}

package cccev.test.f2.concept.data

import cccev.core.concept.entity.InformationConcept
import cccev.core.concept.entity.InformationConceptRepository
import cccev.dsl.model.DataUnitId
import cccev.dsl.model.InformationConceptId
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.informationConcept(conceptRepository: InformationConceptRepository) = AssertionInformationConcept(conceptRepository)

class AssertionInformationConcept(
    private val repository: InformationConceptRepository
): AssertionApiEntity<InformationConcept, InformationConceptId, AssertionInformationConcept.InformationConceptAssert>() {

    override suspend fun findById(id: InformationConceptId) = repository.findById(id)
    override suspend fun assertThat(entity: InformationConcept) = InformationConceptAssert(entity)

    inner class InformationConceptAssert(
        private val concept: InformationConcept
    ) {
        fun hasFields(
            id: InformationConceptId = concept.id,
            name: String = concept.name,
            unit: DataUnitId? = concept.unit.id,
            description: String? = concept.description,
            expressionOfExpectedValue: String? = concept.expressionOfExpectedValue,
            dependencies: List<InformationConceptId>? = concept.dependencies.map { it.id }
        ) = also {
            Assertions.assertThat(concept.id).isEqualTo(id)
            Assertions.assertThat(concept.name).isEqualTo(name)
            Assertions.assertThat(concept.unit.id).isEqualTo(unit)
            Assertions.assertThat(concept.description).isEqualTo(description)
            Assertions.assertThat(concept.expressionOfExpectedValue).isEqualTo(expressionOfExpectedValue)
            Assertions.assertThat(concept.dependencies.map { it.id }).containsExactlyInAnyOrderElementsOf(dependencies)
        }
    }
}

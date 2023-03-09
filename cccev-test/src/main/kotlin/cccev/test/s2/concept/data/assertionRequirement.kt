package cccev.test.s2.concept.data

import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.projection.api.entity.concept.InformationConceptRepository
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.concept.domain.InformationConceptState
import cccev.s2.unit.domain.DataUnitId
import cccev.test.AssertionCrudEntity
import fixers.bdd.assertion.AssertionBdd
import org.assertj.core.api.Assertions

fun AssertionBdd.informationConcept(conceptRepository: InformationConceptRepository) = AssertionInformationConcept(conceptRepository)

class AssertionInformationConcept(
    override val repository: InformationConceptRepository
): AssertionCrudEntity<InformationConceptEntity, InformationConceptId, AssertionInformationConcept.InformationConceptAssert>() {

    override suspend fun assertThat(entity: InformationConceptEntity) = InformationConceptAssert(entity)

    inner class InformationConceptAssert(
        private val concept: InformationConceptEntity
    ) {
        fun hasFields(
            id: InformationConceptId = concept.id,
            status: InformationConceptState = concept.status,
            name: String = concept.name,
            hasUnit: DataUnitId = concept.hasUnit.id,
            description: String = concept.description,
            expressionOfExpectedValue: String? = concept.expressionOfExpectedValue,
            dependsOn: List<InformationConceptId> = concept.dependsOn.map { it.id }
        ) = also {
            Assertions.assertThat(concept.id).isEqualTo(id)
            Assertions.assertThat(concept.status).isEqualTo(status)
            Assertions.assertThat(concept.name).isEqualTo(name)
            Assertions.assertThat(concept.hasUnit.id).isEqualTo(hasUnit)
            Assertions.assertThat(concept.description).isEqualTo(description)
            Assertions.assertThat(concept.expressionOfExpectedValue).isEqualTo(expressionOfExpectedValue)
            Assertions.assertThat(concept.dependsOn.map { it.id }).containsExactlyInAnyOrderElementsOf(dependsOn)
        }
    }
}

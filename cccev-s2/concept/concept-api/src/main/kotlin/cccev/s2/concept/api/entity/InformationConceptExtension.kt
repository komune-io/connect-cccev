package cccev.s2.concept.api.entity

import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.s2.concept.domain.model.InformationConcept

fun InformationConceptEntity.toInformationConcept() = InformationConcept(
    id = id,
    name = name,
    unitId = hasUnit.id,
    description = description,
    expressionOfExpectedValue = expressionOfExpectedValue,
    dependsOn = dependsOn.map { it.id }
)

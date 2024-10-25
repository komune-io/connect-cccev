package cccev.f2.concept.model

import cccev.core.concept.entity.InformationConcept
import cccev.core.concept.model.InformationConceptIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.unit.model.flattenTo

fun InformationConcept.flattenTo(graph: CccevFlatGraph): InformationConceptIdentifier {
    graph.concepts[identifier] = InformationConceptFlat(
        id = id,
        identifier = identifier,
        name = name,
        unitIdentifier = unit.flattenTo(graph),
        description = description,
        expressionOfExpectedValue = expressionOfExpectedValue,
        dependsOn = dependencies.map { it.flattenTo(graph) }
    )
    return identifier
}

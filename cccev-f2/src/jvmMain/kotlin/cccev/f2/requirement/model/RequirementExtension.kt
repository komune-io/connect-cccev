package cccev.f2.requirement.model

import cccev.core.requirement.entity.Requirement
import cccev.core.requirement.model.RequirementIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo
import cccev.f2.evidencetype.model.flattenTo

fun Requirement.flattenTo(graph: CccevFlatGraph): RequirementIdentifier {
    graph.requirements[identifier] = RequirementFlat(
        id = id,
        identifier = identifier,
        kind = kind.name,
        description = description,
        type = type,
        name = name,
        subRequirementIds = subRequirements.map { it.flattenTo(graph) },
        conceptIdentifiers = concepts.map { it.flattenTo(graph) },
        evidenceTypeIds = evidenceTypes.map { it.flattenTo(graph) },
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies.map { it.flattenTo(graph) },
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies.map { it.flattenTo(graph) },
        order = order,
        properties = properties
    )
    return identifier
}

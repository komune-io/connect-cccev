package cccev.f2.certification.model

import cccev.core.certification.entity.SupportedValueEntity
import cccev.dsl.model.SupportedValueId
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo

fun SupportedValueEntity.flattenTo(graph: CccevFlatGraph): SupportedValueId {
    graph.supportedValues[id] = SupportedValueFlat(
        id = id,
        identifier = identifier,
        value = value,
        conceptIdentifier = concept.flattenTo(graph),
    )
    return id
}

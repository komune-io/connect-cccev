package cccev.f2.certification.model

import cccev.core.certification.entity.SupportedValue
import cccev.core.certification.model.SupportedValueId
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo

fun SupportedValue.flattenTo(graph: CccevFlatGraph): SupportedValueId {
    graph.supportedValues[id] = SupportedValueFlat(
        id = id,
        value = value,
        conceptIdentifier = concept.flattenTo(graph),
    )
    return id
}

package cccev.f2.evidencetype.model

import cccev.core.evidencetype.entity.EvidenceType
import cccev.core.evidencetype.model.EvidenceTypeId
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo

fun EvidenceType.flattenTo(graph: CccevFlatGraph): EvidenceTypeId {
    graph.evidenceTypes[id] = EvidenceTypeFlat(
        id = id,
        name = name,
        conceptIdentifiers = concepts.map { it.flattenTo(graph) }
    )
    return id
}

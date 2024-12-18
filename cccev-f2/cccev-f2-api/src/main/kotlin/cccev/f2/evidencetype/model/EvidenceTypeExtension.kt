package cccev.f2.evidencetype.model

import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.dsl.model.EvidenceTypeId
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo

fun EvidenceTypeEntity.flattenTo(graph: CccevFlatGraph): EvidenceTypeId {
    graph.evidenceTypes[id] = EvidenceTypeFlat(
        id = id,
        identifier = identifier,
        name = name,
        conceptIdentifiers = concepts.map { it.flattenTo(graph) }
    )
    return id
}

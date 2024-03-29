package cccev.f2.concept.api.model

import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.SupportedValue
import cccev.f2.commons.CertificationFlatGraph
import cccev.f2.concept.domain.model.InformationConceptDTOBase
import cccev.f2.concept.domain.model.InformationConceptFlat
import cccev.f2.concept.domain.model.RequestInformationConceptDTOBase
import cccev.f2.evidence.api.model.toEvidenceTypeListChoices
import cccev.f2.evidence.domain.model.EvidenceTypeListDTO
import cccev.f2.unit.api.model.flattenTo
import cccev.f2.unit.api.model.toDTO
import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.s2.concept.domain.InformationConceptIdentifier
import cccev.s2.concept.domain.model.InformationConcept
import java.util.UUID

// TODO move to request-f2 module?
fun InformationConceptBase.toRequestDTO(
    evidenceTypeLists: List<EvidenceTypeListDTO>,
    supportedValue: SupportedValue?
) = RequestInformationConceptDTOBase(
    identifier = identifier,
    name = name,
    unit = unit,
    type = type,
    description = description,
    expressionOfExpectedValue = expressionOfExpectedValue,
    dependsOn = dependsOn,
    evidenceTypeChoices = evidenceTypeLists.toEvidenceTypeListChoices(),
    supportedValue = supportedValue ?: SupportedValue(
        identifier = UUID.randomUUID().toString(),
        providesValueFor = identifier
    )
)

fun InformationConcept.toDTO() = InformationConceptDTOBase(
    id = id,
    identifier = identifier,
    name = name,
    unit = unit?.toDTO(),
    description = description,
    expressionOfExpectedValue = expressionOfExpectedValue,
    dependsOn = dependsOn
)

fun InformationConceptEntity.flattenTo(graph: CertificationFlatGraph): InformationConceptIdentifier {
    graph.concepts[identifier] = InformationConceptFlat(
        id = id,
        identifier = identifier,
        name = name,
        unitIdentifier = hasUnit?.flattenTo(graph).orEmpty(),
        description = description,
        expressionOfExpectedValue = expressionOfExpectedValue,
        dependsOn = dependsOn?.map { it.flattenTo(graph) }
    )
    return identifier
}

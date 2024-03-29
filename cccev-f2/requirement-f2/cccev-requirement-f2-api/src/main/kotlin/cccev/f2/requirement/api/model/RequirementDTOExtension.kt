package cccev.f2.requirement.api.model

import cccev.commons.utils.parseJsonTo
import cccev.f2.commons.CertificationFlatGraph
import cccev.f2.concept.api.model.flattenTo
import cccev.f2.concept.api.model.toDTO
import cccev.f2.evidence.type.domain.model.EvidenceTypeListDTOBase
import cccev.f2.requirement.domain.model.RequirementDTOBase
import cccev.f2.requirement.domain.model.RequirementFlat
import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.s2.concept.domain.model.InformationConcept
import cccev.s2.evidence.type.domain.EvidenceTypeListId
import cccev.s2.requirement.domain.model.Requirement
import cccev.s2.requirement.domain.model.RequirementIdentifier

suspend fun Requirement.toDTO(
    getEvidenceTypeList: suspend (EvidenceTypeListId) -> EvidenceTypeListDTOBase
): RequirementDTOBase = RequirementDTOBase(
    id = id,
    identifier = identifier,
    kind = kind.name,
    description = description,
    type = type,
    name = name,
    hasRequirement = hasRequirement.map { it.toDTO(getEvidenceTypeList) },
    hasQualifiedRelation = hasQualifiedRelation.orEmpty(),
    hasConcept = hasConcept.map(InformationConcept::toDTO),
    hasEvidenceTypeList = hasEvidenceTypeList.map { getEvidenceTypeList(it) },
    enablingCondition = enablingCondition,
    enablingConditionDependencies = enablingConditionDependencies.map { it.toDTO() },
    required = required,
    validatingCondition = validatingCondition,
    validatingConditionDependencies = validatingConditionDependencies.map { it.toDTO() },
    order = order,
    properties = properties
)

fun RequirementEntity.flattenTo(graph: CertificationFlatGraph): RequirementIdentifier {
    graph.requirements[identifier] = RequirementFlat(
        id = id,
        identifier = identifier,
        kind = kind.name,
        description = description,
        type = type,
        name = name,
        hasRequirement = hasRequirementTmp.map { it.flattenTo(graph) },
        hasConcept = hasConcept.map { it.flattenTo(graph) },
        hasEvidenceTypeList = emptyList(), // TODO
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies.map { it.flattenTo(graph) },
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies.map { it.flattenTo(graph) },
        order = order,
        properties = properties?.parseJsonTo<Map<String, String>>(),
    )
    return identifier
}

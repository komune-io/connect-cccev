package cccev.f2.requirement.domain.command

import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.evidence.type.domain.EvidenceTypeListId
import cccev.s2.framework.domain.FrameworkId
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.model.RequirementKind
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias CriterionCreateFunction = F2Function<CriterionCreateCommandDTOBase, RequirementCreatedEvent>

@JsExport
@JsName("CriterionCreateCommandDTO")
interface CriterionCreateCommandDTO: RequirementCreateCommandDTO

@Serializable
data class CriterionCreateCommandDTOBase(
    override val identifier: String? = null,
    override val name: String? = null,
    override val description: String? = null,
    override val type: String? = null,
    override val isDerivedFrom: List<FrameworkId> = emptyList(),
    override val hasRequirement: List<RequirementId> = emptyList(),
    override val hasConcept: List<InformationConceptId> = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListId> = emptyList(),
    override var hasQualifiedRelation: Map<String, List<RequirementId>> = emptyMap()
): CriterionCreateCommandDTO{
    override val kind: String = RequirementKind.CRITERION.name
}

@JsExport
@JsName("CriterionCreatedEventDTO")
interface CriterionCreatedEventDTO: cccev.s2.requirement.domain.command.RequirementCreatedEventDTO

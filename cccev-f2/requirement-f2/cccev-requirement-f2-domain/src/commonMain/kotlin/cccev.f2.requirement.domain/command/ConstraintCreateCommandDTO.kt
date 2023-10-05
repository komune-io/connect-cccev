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

typealias ConstraintCreateFunction = F2Function<ConstraintCreateCommandDTOBase, RequirementCreatedEvent>

@JsExport
@JsName("ConstraintCreateCommandDTO")
interface ConstraintCreateCommandDTO: RequirementCreateCommandDTO

@Serializable
data class ConstraintCreateCommandDTOBase(
    override val identifier: String? = null,
    override val name: String? = null,
    override val description: String? = null,
    override val type: String? = null,
    override val isDerivedFrom: List<FrameworkId> = emptyList(),
    override val hasRequirement: List<RequirementId> = emptyList(),
    override val hasConcept: List<InformationConceptId> = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListId> = emptyList(),
    override var hasQualifiedRelation: Map<String, List<RequirementId>> = emptyMap()
): ConstraintCreateCommandDTO {
    override val kind: String = RequirementKind.CONSTRAINT.name
}

@JsExport
@JsName("ConstraintCreatedEventDTO")
interface ConstraintCreatedEventDTO: cccev.s2.requirement.domain.command.RequirementCreatedEventDTO

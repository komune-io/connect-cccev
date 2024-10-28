package cccev.f2.requirement.model

import cccev.f2.concept.model.InformationConceptIdentifier
import cccev.f2.evidencetype.model.EvidenceTypeListId
import cccev.f2.requirement.model.RequirementId
import cccev.f2.requirement.model.RequirementIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
interface RequirementFlatDTO {
    val id: RequirementId
    val identifier: RequirementIdentifier
    val kind: String
    val description: String?
    val type: String?
    val name: String?
    val subRequirementIds: List<RequirementId>
    val conceptIdentifiers: List<InformationConceptIdentifier>
    val evidenceTypeIds: List<EvidenceTypeListId>
    val enablingCondition: String?
    val enablingConditionDependencies: List<InformationConceptIdentifier>
    val required: Boolean
    val validatingCondition: String?
    val validatingConditionDependencies: List<InformationConceptIdentifier>
    val order: Int?
    val properties: Map<String, String>?
}

@Serializable
data class RequirementFlat(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
    override val kind: String,
    override val description: String?,
    override val type: String?,
    override val name: String?,
    override val subRequirementIds: List<RequirementId>,
    override val conceptIdentifiers: List<InformationConceptIdentifier>,
    override val evidenceTypeIds: List<EvidenceTypeListId>,
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>,
    override val order: Int?,
    override val properties: Map<String, String>?
): RequirementFlatDTO

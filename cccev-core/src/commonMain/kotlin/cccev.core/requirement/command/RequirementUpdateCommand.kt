package cccev.core.requirement.command

import cccev.core.concept.model.InformationConceptId
import cccev.core.evidencetype.model.EvidenceTypeId
import cccev.core.requirement.D2RequirementPage
import cccev.core.requirement.model.RequirementId
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Update a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 10
 */
typealias RequirementUpdateFunction = F2Function<RequirementUpdateCommand, RequirementUpdatedEvent>

/**
 * @d2 command
 * @parent [RequirementUpdateFunction]
 */
@JsExport
interface RequirementUpdateCommandDTO {
    /**
     * Identifier of the requirement to update.
     */
    val id: RequirementId

    /**
     * Name of the requirement.
     * @example [cccev.core.requirement.model.Requirement.name]
     */
    val name: String?

    /**
     * Description of the requirement.
     * @example [cccev.core.requirement.model.Requirement.description]
     */
    val description: String?

    val type: String?
    val conceptIds: List<InformationConceptId>
    val evidenceTypeIds: List<EvidenceTypeId>
    val subRequirementIds: List<RequirementId>
    val enablingCondition: String?
    val enablingConditionDependencies: List<InformationConceptId>
    val required: Boolean
    val validatingCondition: String?
    val validatingConditionDependencies: List<InformationConceptId>
    val evidenceValidatingCondition: String?
    val order: Int?
    val properties: Map<String, String>?
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementUpdateCommand(
    override val id: RequirementId,
    override val name: String?,
    override val description: String?,
    override val type: String?,
    override val conceptIds: List<InformationConceptId>,
    override val evidenceTypeIds: List<EvidenceTypeId>,
    override val subRequirementIds: List<RequirementId>,
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptId>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptId>,
    override val evidenceValidatingCondition: String?,
    override val order: Int?,
    override val properties: Map<String, String>?
): RequirementUpdateCommandDTO

/**
 * @d2 event
 * @parent [RequirementUpdateFunction]
 */
@JsExport
interface RequirementUpdatedEventDTO {
    /**
     * Identifier of the updated requirement.
     */
    val id: RequirementId
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementUpdatedEvent(
    override val id: RequirementId,
): RequirementUpdatedEventDTO

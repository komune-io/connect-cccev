package cccev.f2.requirement.command

import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.RequirementId
import cccev.dsl.model.RequirementIdentifier
import cccev.dsl.model.RequirementKind
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Create a new requirement.
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 * @order 10
 */
typealias RequirementCreateFunction = F2Function<RequirementCreateCommand, RequirementCreatedEvent>

/**
 * @d2 command
 * @parent [RequirementCreateFunction]
 */
interface RequirementCreateCommandDTO {
    /**
     * A custom identifier for the requirement
     * @example [cccev.dsl.model.Requirement.identifier]
     */
    val identifier: RequirementIdentifier?

    /**
     * Subtype used for the requirement.
     * @example [cccev.dsl.model.Requirement.kind]
     */
    val kind: RequirementKind

    /**
     * Name of the requirement.
     * @example [cccev.dsl.model.Requirement.name]
     */
    val name: String?

    /**
     * Description of the requirement.
     * @example [cccev.dsl.model.Requirement.description]
     */
    val description: String?

    /**
     * @ref [cccev.dsl.model.Requirement.type]
     */
    val type: String?

    /**
     * Sub-requirements that must be fulfilled for the requirement to be validated.
     * @example [cccev.dsl.model.Requirement.hasRequirement]
     */
    val subRequirementIds: List<RequirementId>?

    /**
     * Concepts used by the requirement
     * @example [cccev.dsl.model.Requirement.hasConcept]
     */
    val conceptIds: List<InformationConceptId>?

    /**
     * Evidences that must be provided for the requirement to be validated. <br/>
     * @example [cccev.dsl.model.Requirement.hasEvidenceTypeList]
     */
    val evidenceTypeListIds: List<EvidenceTypeId>?

    /**
     * @ref [cccev.dsl.model.Requirement.enablingCondition]
     */
    val enablingCondition: String?

    /**
     * @ref [cccev.dsl.model.Requirement.enablingConditionDependencies]
     */
    val enablingConditionDependencies: List<InformationConceptId>?

    /**
     * @ref [cccev.dsl.model.Requirement.required]
     */
    val required: Boolean

    /**
     * @ref [cccev.dsl.model.Requirement.validatingCondition]
     */
    val validatingCondition: String?

    /**
     * @ref [cccev.dsl.model.Requirement.validatingConditionDependencies]
     */
    val validatingConditionDependencies: List<InformationConceptId>?

    /**
     * @ref [cccev.dsl.model.Requirement.evidenceValidatingCondition]
     */
    val evidenceValidatingCondition: String?

    /**
     * @ref [cccev.dsl.model.Requirement.order]
     */
    val order: Int?

    /**
     * @ref [cccev.dsl.model.Requirement.properties]
     */
    val properties: Map<String, String>?
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementCreateCommand(
    override val identifier: String? = null,
    override val kind: RequirementKind,
    override val name: String? = null,
    override val description: String? = null,
    override val type: String? = null,
    override val subRequirementIds: List<RequirementId>? = null,
    override val conceptIds: List<InformationConceptId>? = null,
    override val evidenceTypeListIds: List<EvidenceTypeId>? = null,
    override val enablingCondition: String? = null,
    override val enablingConditionDependencies: List<InformationConceptId>? = null,
    override val required: Boolean = true,
    override val validatingCondition: String? = null,
    override val validatingConditionDependencies: List<InformationConceptId>? = null,
    override val evidenceValidatingCondition: String? = null,
    override val order: Int? = null,
    override val properties: Map<String, String>? = null,
): RequirementCreateCommandDTO

/**
 * @d2 event
 * @parent [RequirementCreateFunction]
 */
@JsExport
interface RequirementCreatedEventDTO {
    /**
     * Id of the created requirement
     */
    val id: RequirementId
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementCreatedEvent(
    override val id: RequirementId,
): RequirementCreatedEventDTO

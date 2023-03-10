package cccev.s2.requirement.domain.command

import cccev.dsl.model.EvidenceTypeListId
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementInitCommand
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.requirement.domain.model.RequirementKind
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create a new requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 10
 */
interface RequirementCreateFunction

/**
 * @d2 command
 * @parent [RequirementCreateFunction]
 */
@Serializable
data class RequirementCreateCommand(
    /**
     * A custom identifier for the requirement
     * @example [cccev.s2.requirement.domain.model.Requirement.identifier]
     */
    val identifier: String?,

    /**
     * Subtype used for the requirement.
     * @example [cccev.s2.requirement.domain.model.Requirement.kind]
     */
    val kind: RequirementKind,

    /**
     * Name of the requirement.
     * @example [cccev.s2.requirement.domain.model.Requirement.name]
     */
    val name: String?,

    /**
     * Description of the requirement. <br/>
     * If the requirement is a constraint, this field must contain an expression returning a boolean.
     * For now, this expression will be evaluated using a Kotlin engine. <br />
     * The expression may contain known information concepts, identified by their id. They must be declared in the `hasConcept` field.
     * @example [cccev.s2.requirement.domain.model.Requirement.description]
     */
    val description: String?,

    /**
     * Sub-requirements that must be fulfilled for the requirement to be validated.
     * @example [cccev.s2.requirement.domain.model.Requirement.hasRequirement]
     */
    val hasRequirement: List<RequirementIdentifier>,

    /**
     * Described and/or categorised relation to another Requirement. <br/>
     * This property leaves the possiblity to define a qualified relation from Requirement
     * to Information Requirement or Constraint as well as a qualified relation
     * from Requirement to Requirement. A use case would be to specialize an EU requirement
     * in Member States' specific requirements.
     * @example [["baee57d9-7f0a-4cb0-92e5-402b80c18c74"]]
     */
    var hasQualifiedRelation: List<RequirementIdentifier>?,

    /**
     * Concepts used by the requirement
     * @example [cccev.s2.requirement.domain.model.Requirement.hasConcept]
     */
    val hasConcept: List<InformationConceptId>,

    /**
     * Evidences that must be provided for the requirement to be validated. <br/>
     * This list represents an OR-relation, i.e. only one of the specified evidence lists has to be fully provided.
     * @example [cccev.s2.requirement.domain.model.Requirement.hasEvidenceTypeList]
     */
    val hasEvidenceTypeList: List<EvidenceTypeListId>
): RequirementInitCommand

@JsExport
@JsName("RequirementCreatedEventDTO")
interface RequirementCreatedEventDTO: RequirementEvent {
    /**
     * Identifier of the created requirement
     */
    override val id: RequirementId

    /**
     * @ref [RequirementCreateCommand.identifier]
     */
    val identifier: RequirementIdentifier?

    /**
     * @ref [RequirementCreateCommand.kind]
     */
    val kind: RequirementKind

    /**
     * @ref [RequirementCreateCommand.name]
     */
    val name: String?

    /**
     * @ref [RequirementCreateCommand.description]
     */
    val description: String?

    /**
     * @ref [RequirementCreateCommand.hasRequirement]
     */
    val hasRequirement: List<RequirementIdentifier>

    /**
     * @ref [RequirementCreateCommand.hasQualifiedRelation]
     */
    val hasQualifiedRelation: List<RequirementIdentifier>?

    /**
     * @ref [RequirementCreateCommand.hasConcept]
     */
    val hasConcept: List<InformationConceptId>

    /**
     * @ref [RequirementCreateCommand.hasEvidenceTypeList]
     */
    val hasEvidenceTypeList: List<EvidenceTypeListId>

    /**
     * State of the created requirement
     * @example "CREATED"
     */
    val status: RequirementState
}

/**
 * @d2 event
 * @parent [RequirementCreateFunction]
 */
@Serializable
data class RequirementCreatedEvent(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier?,
    override val kind: RequirementKind,
    override val name: String?,
    override val description: String?,
    override val hasRequirement: List<RequirementId>,
    override val hasQualifiedRelation: List<RequirementId>?,
    override val hasConcept: List<InformationConceptId>,
    override val hasEvidenceTypeList: List<EvidenceTypeListId>,
    override val status: RequirementState
): RequirementCreatedEventDTO

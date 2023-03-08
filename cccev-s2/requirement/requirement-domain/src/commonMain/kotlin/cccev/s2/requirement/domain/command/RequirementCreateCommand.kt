package cccev.s2.requirement.domain.command

import cccev.dsl.model.EvidenceTypeListId
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementInitCommand
import cccev.s2.requirement.domain.model.Requirement
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
    val hasRequirement: List<RequirementId>,

    /**
     * A reference between a sub-Requirement and its parent Requirement.
     * The relation between a parent Requirement and a sub-Requirement can be complex.
     * Therefore, qualified relations (see `hasQualifiedRelation`) can be used to represent
     * this relationship on its own and qualify it with additional information such as a date, a place.
     * This is left to implementers. In the case where the purpose is to link the two Requirements
     * without additional information, the simple relationship as proposed here can be directly used.
     *  @example [["b25975b6-f4ff-4773-b535-9a18192b30de"]]
     */
    var isRequirementOf: List<RequirementId>?,
    /**
     * Described and/or categorised relation to another Requirement. <br/>
     * This property leaves the possiblity to define a qualified relation from Requirement
     * to Information Requirement or Constraint as well as a qualified relation
     * from Requirement to Requirement. A use case would be to specialize an EU requirement
     * in Member States' specific requirements.
     * @example [["baee57d9-7f0a-4cb0-92e5-402b80c18c74"]]
     */
    var hasQualifiedRelation: List<RequirementId>?,

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
     * Identifier of the created requirement.
     */
    val requirement: Requirement
}

/**
 * @d2 event
 * @parent [RequirementCreateFunction]
 */
@Serializable
data class RequirementCreatedEvent(
    override val requirement: Requirement,
): RequirementCreatedEventDTO {
    override fun s2Id() = requirement.id
}

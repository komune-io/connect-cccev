package cccev.s2.requirement.domain.model

import cccev.dsl.model.EvidenceTypeListId
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.Requirement
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementState
import kotlinx.serialization.Serializable
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

typealias RequirementIdentifier = String

/**
 * @d2 model
 * @parent [D2RequirementPage]
 * @order 10
 */
@Serializable
data class Requirement(
    /**
     * Id of the requirement.
     */
    val id: RequirementId,

    /**
     * A custom identifier for the requirement
     * @example "cloudAlt"
     */
    val identifier: RequirementIdentifier?,

    /**
     * Subtype used for the requirement.
     * @example "CONSTRAINT"
     */
    val kind: RequirementKind,

    /**
     * Name of the requirement.
     * @example "High clouds altitude"
     */
    val name: String?,

    /**
     * Description of the requirement. <br/>
     * If the requirement is a constraint, this field must contain an expression returning a boolean.
     * For now, this expression will be evaluated using a Kotlin engine. <br />
     * The expression may contain known information concepts, identified by their id. They must be declared in the `hasConcept` field.
     * @example "altitudeInMeters >= 6000"
     */
    val description: String?,

    /**
     * Sub-requirements that must be fulfilled for the requirement to be validated.
     * @example [["78e1e5f3-6e81-411d-afe6-aa7e6dae59b4"]]
     */
    val hasRequirement: List<RequirementIdentifier>,

    /**
     * A reference between a sub-Requirement and its parent Requirement.
     * The relation between a parent Requirement and a sub-Requirement can be complex.
     * Therefore, qualified relations (see `hasQualifiedRelation`) can be used to represent
     * this relationship on its own and qualify it with additional information such as a date, a place.
     * This is left to implementers. In the case where the purpose is to link the two Requirements
     * without additional information, the simple relationship as proposed here can be directly used.
     *  @example [["b25975b6-f4ff-4773-b535-9a18192b30de"]]
     */
    var isRequirementOf: List<RequirementIdentifier>?,

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
     * @example [["altitudeInMeters"]]
     */
    val hasConcept: List<InformationConceptId>,

    /**
     * Evidences that must be provided for the requirement to be validated. <br/>
     * This list represents an OR-relation, i.e. only one of the specified evidence lists has to be fully provided.
     * @example [["dc006198-067e-4a58-8672-7d5377ae022b"]]
     */
    val hasEvidenceTypeList: List<EvidenceTypeListId>,
    /**
     * The state of the requirement.
     * @example ["CREATED"]
     */
    val state: RequirementState
): WithS2Id<RequirementId>, WithS2State<RequirementState> {
    override fun s2Id() = id
    override fun s2State() = state
}

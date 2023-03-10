package cccev.s2.requirement.domain.command

import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementCommand
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import kotlinx.serialization.Serializable

/**
 * Add a list of evidence type list to a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 70
 */
interface RequirementAddEvidenceTypeListsFunction

/**
 * @d2 command
 * @parent [RequirementAddEvidenceTypeListsFunction]
 */
data class RequirementAddEvidenceTypeListsCommand(
    /**
     * Id of the requirement to add evidence type lists to.
     */
    override val id: RequirementId,

    /**
     * Ids of the evidence type lists to add.
     */
    val evidenceTypeListIds: List<EvidenceTypeListId>
): RequirementCommand

/**
 * @d2 event
 * @parent [RequirementAddEvidenceTypeListsFunction]
 */
@Serializable
data class RequirementAddedEvidenceTypeListsEvent(
    /** @ref [RequirementAddEvidenceTypeListsCommand.id] */
    override val id: RequirementId,

    /** @ref [RequirementAddEvidenceTypeListsCommand.evidenceTypeListIds] */
    val evidenceTypeListIds: List<EvidenceTypeListId>
): RequirementEvent

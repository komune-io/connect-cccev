package cccev.s2.requirement.domain.command

import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementCommand
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import kotlinx.serialization.Serializable

/**
 * Remove a list of evidence type list from a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 80
 */
interface RequirementRemoveEvidenceTypeListsFunction

/**
 * @d2 command
 * @parent [RequirementRemoveEvidenceTypeListsFunction]
 */
data class RequirementRemoveEvidenceTypeListsCommand(
    /**
     * Id of the requirement to remove evidence type lists from.
     */
    override val id: RequirementId,

    /**
     * Ids of the evidence type lists to remove.
     */
    val evidenceTypeListIds: List<EvidenceTypeListId>
): RequirementCommand

/**
 * @d2 event
 * @parent [RequirementRemoveEvidenceTypeListsFunction]
 */
@Serializable
data class RequirementRemovedEvidenceTypeListsEvent(
    /** @ref [RequirementRemoveEvidenceTypeListsCommand.id] */
    override val id: RequirementId,

    /** @ref [RequirementRemoveEvidenceTypeListsCommand.evidenceTypeListIds] */
    val evidenceTypeListIds: List<EvidenceTypeListId>
): RequirementEvent

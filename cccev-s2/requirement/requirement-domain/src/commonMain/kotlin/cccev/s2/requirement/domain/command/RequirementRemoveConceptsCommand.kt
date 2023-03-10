package cccev.s2.requirement.domain.command

import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementCommand
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import kotlinx.serialization.Serializable

/**
 * Remove a list of information concept from a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 60
 */
interface RequirementRemoveConceptsFunction

/**
 * @d2 command
 * @parent [RequirementRemoveConceptsFunction]
 */
data class RequirementRemoveConceptsCommand(
    /**
     * Id of the requirement to remove information concepts from.
     */
    override val id: RequirementId,

    /**
     * Ids of the information concepts to remove.
     */
    val conceptIds: List<InformationConceptId>
): RequirementCommand

/**
 * @d2 event
 * @parent [RequirementRemoveConceptsFunction]
 */
@Serializable
data class RequirementRemovedConceptsEvent(
    /** @ref [RequirementRemoveConceptsCommand.id] */
    override val id: RequirementId,

    /** @ref [RequirementRemoveConceptsCommand.conceptIds] */
    val conceptIds: List<InformationConceptId>
): RequirementEvent

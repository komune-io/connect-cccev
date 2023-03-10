package cccev.s2.requirement.domain.command

import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementCommand
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import kotlinx.serialization.Serializable

/**
 * Remove a list of sub-requirement from a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 40
 */
interface RequirementRemoveRequirementsFunction

/**
 * @d2 command
 * @parent [RequirementRemoveRequirementsFunction]
 */
data class RequirementRemoveRequirementsCommand(
    /**
     * Id of the requirement to remove sub-requirements from.
     */
    override val id: RequirementId,

    /**
     * Ids of the sub-requirements to remove.
     * @example [["8e411870-9a8c-4d7a-91b6-496148c6f5c5", "f31cf8df-44f2-4fef-bc20-09a173032bb2"]]
     */
    val requirementIds: List<RequirementId>
): RequirementCommand

/**
 * @d2 event
 * @parent [RequirementRemoveRequirementsFunction]
 */
@Serializable
data class RequirementRemovedRequirementsEvent(
    /** @ref [RequirementRemoveRequirementsCommand.id] */
    override val id: RequirementId,

    /** @ref [RequirementRemoveRequirementsCommand.requirementIds] */
    val requirementIds: List<RequirementId>
): RequirementEvent

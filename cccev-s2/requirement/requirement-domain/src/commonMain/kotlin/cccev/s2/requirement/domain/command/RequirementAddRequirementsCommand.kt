package cccev.s2.requirement.domain.command

import cccev.s2.requirement.domain.D2RequirementPage
import cccev.s2.requirement.domain.RequirementCommand
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import kotlinx.serialization.Serializable

/**
 * Add a list of sub-requirement to a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 30
 */
interface RequirementAddRequirementsFunction

/**
 * @d2 command
 * @parent [RequirementAddRequirementsFunction]
 */
data class RequirementAddRequirementsCommand(
    /**
     * Id of the requirement to add sub-requirements to.
     */
    override val id: RequirementId,

    /**
     * Ids of the sub-requirements to add.
     * @example [["8e411870-9a8c-4d7a-91b6-496148c6f5c5", "f31cf8df-44f2-4fef-bc20-09a173032bb2"]]
     */
    val requirementIds: List<RequirementId>
): RequirementCommand

/**
 * @d2 event
 * @parent [RequirementAddRequirementsFunction]
 */
@Serializable
data class RequirementAddedRequirementsEvent(
    /** @ref [RequirementAddRequirementsCommand.id] */
    override val id: RequirementId,

    /** @ref [RequirementAddRequirementsCommand.requirementIds] */
    val requirementIds: List<RequirementId>
): RequirementEvent

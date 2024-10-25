package cccev.core.requirement.command

import cccev.core.requirement.D2RequirementPage import cccev.core.requirement.model.RequirementId
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Remove a list of sub-requirement from a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 40
 */
typealias RequirementRemoveRequirementsFunction = F2Function<RequirementRemoveRequirementsCommand, RequirementRemovedRequirementsEvent>

/**
 * @d2 command
 * @parent [RequirementRemoveRequirementsFunction]
 */
data class RequirementRemoveRequirementsCommand(
    /**
     * Id of the requirement to remove sub-requirements from.
     */
    val id: RequirementId,

    /**
     * Ids of the sub-requirements to remove.
     * @example [["8e411870-9a8c-4d7a-91b6-496148c6f5c5", "f31cf8df-44f2-4fef-bc20-09a173032bb2"]]
     */
    val requirementIds: List<RequirementId> = emptyList()
)

/**
 * @d2 event
 * @parent [RequirementRemoveRequirementsFunction]
 */
@Serializable
data class RequirementRemovedRequirementsEvent(
    /** @ref [RequirementRemoveRequirementsCommand.id] */
    val id: RequirementId,

    /** @ref [RequirementRemoveRequirementsCommand.requirementIds] */
    val requirementIds: List<RequirementId> = emptyList()
)

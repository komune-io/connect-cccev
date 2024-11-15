package cccev.f2.requirement.command

import cccev.dsl.model.RequirementId
import cccev.dsl.model.d2.D2RequirementPage
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Add a list of sub-requirement to a requirement.
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 * @order 30
 */
typealias RequirementAddRequirementsFunction = F2Function<RequirementAddRequirementsCommand, RequirementAddedRequirementsEvent>

/**
 * @d2 command
 * @parent [RequirementAddRequirementsFunction]
 */
@JsExport
interface RequirementAddRequirementsCommandDTO {
    /**
     * Id of the requirement to add sub-requirements to.
     */
    val id: RequirementId

    /**
     * Ids of the sub-requirements to add.
     * @example [["8e411870-9a8c-4d7a-91b6-496148c6f5c5", "f31cf8df-44f2-4fef-bc20-09a173032bb2"]]
     */
    val requirementIds: List<RequirementId>
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementAddRequirementsCommand(
    override val id: RequirementId,
    override val requirementIds: List<RequirementId> = emptyList()
): RequirementAddRequirementsCommandDTO

/**
 * @d2 event
 * @parent [RequirementAddRequirementsFunction]
 */
@JsExport
interface RequirementAddedRequirementsEventDTO {
    /** @ref [RequirementAddRequirementsCommand.id] */
    val id: RequirementId

    /** @ref [RequirementAddRequirementsCommand.requirementIds] */
    val requirementIds: List<RequirementId>
}

/**
 * @d2 inherit
 */
@Serializable
data class RequirementAddedRequirementsEvent(
    override val id: RequirementId,
    override val requirementIds: List<RequirementId> = emptyList()
): RequirementAddedRequirementsEventDTO

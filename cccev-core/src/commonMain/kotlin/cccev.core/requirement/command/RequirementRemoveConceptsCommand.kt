package cccev.core.requirement.command

import cccev.core.concept.model.InformationConceptId
import cccev.core.requirement.model.RequirementId
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Remove a list of information concept from a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 60
 */
typealias RequirementRemoveConceptsFunction = F2Function<RequirementRemoveConceptsCommand, RequirementRemovedConceptsEvent>

/**
 * @d2 command
 * @parent [RequirementRemoveConceptsFunction]
 */
data class RequirementRemoveConceptsCommand(
    /**
     * Id of the requirement from which to remove information concepts.
     */
    val id: RequirementId,

    /**
     * Identifiers of the information concepts to remove.
     */
    val conceptIds: List<InformationConceptId> = emptyList()
)

/**
 * @d2 event
 * @parent [RequirementRemoveConceptsFunction]
 */
@Serializable
data class RequirementRemovedConceptsEvent(
    /**
     * Id of the requirement from which information concepts were removed.
     */
    val id: RequirementId,
)

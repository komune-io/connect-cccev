package cccev.f2.requirement.command

import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.RequirementId
import cccev.f2.requirement.D2RequirementPage
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Add a list of information concept to a requirement.
 * @d2 function
 * @parent [D2RequirementPage]
 * @order 50
 */
typealias RequirementAddConceptsFunction = F2Function<RequirementAddConceptsCommand, RequirementAddedConceptsEvent>

/**
 * @d2 command
 * @parent [RequirementAddConceptsFunction]
 */
data class RequirementAddConceptsCommand(
    /**
     * Id of the requirement to add information concepts to.
     */
    val id: RequirementId,

    /**
     * Ids of the information concepts to add.
     */
    val conceptIds: List<InformationConceptId>
)

/**
 * @d2 event
 * @parent [RequirementAddConceptsFunction]
 */
@Serializable
data class RequirementAddedConceptsEvent(
    /** @ref [RequirementAddConceptsCommand.id] */
    val id: RequirementId,
)

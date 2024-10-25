package cccev.core.requirement.model

/**
 * Indicate which subtype of requirement is being used. <br/>
 * Can be either of: CONSTRAINT, CRITERION, INFORMATION
 * @d2 model
 * @order 20
 * @parent [D2RequirementPage]
 * @visual json "CONSTRAINT"
 */
enum class RequirementKind {
    CONSTRAINT,
    CRITERION,
    INFORMATION
}

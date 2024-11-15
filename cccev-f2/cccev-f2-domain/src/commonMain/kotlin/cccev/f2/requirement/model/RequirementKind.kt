package cccev.f2.requirement.model

/**
 * Indicate which subtype of requirement is being used. <br/>
 * Can be either of: CONSTRAINT, CRITERION, INFORMATION
 * @d2 model
 * @order 20
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 * @example "CONSTRAINT"
 */
enum class RequirementKind {
    CONSTRAINT,
    CRITERION,
    INFORMATION
}

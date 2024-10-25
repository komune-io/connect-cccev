package cccev.dsl.model.builder

import cccev.dsl.model.Code
import cccev.dsl.model.Constraint
import cccev.dsl.model.Criterion
import cccev.dsl.model.InformationRequirement
import cccev.dsl.model.Requirement

class RequirementsLinkedBuilder {

    private var requirements = mutableListOf<Requirement>()

    fun criterion(
    init: CriterionBuilder.() -> Unit,
    ) {
        +CriterionBuilder().apply(init).build()
    }

    fun informationRequirement(
        init: InformationRequirementBuilder.() -> Unit
    ) {
        +InformationRequirementBuilder().apply(init).build()
    }

    fun constraint(
        init: ConstraintBuilder.() -> Unit) {
        +ConstraintBuilder().apply(init).build()
    }

    operator fun Requirement.unaryPlus() {
        this@RequirementsLinkedBuilder.requirements.add(this)
    }

    operator fun Iterable<Requirement>.unaryPlus() {
        this@RequirementsLinkedBuilder.requirements.addAll(this)
    }

    fun build(): List<Requirement> = requirements.toList()
}

class CriterionBuilder : AbstractRequirementBuilder<Criterion>() {
    var bias: Double? = null
    var weight: Double? = null
    var weightingConsiderationDescription: String? = null
    var weightingType: Code? = null

    override fun build(): Criterion = Criterion(
        description = description,
        identifier = identifier,
        name = name,
        type = type,

        hasConcept = hasConcept,
        hasEvidenceTypeList = hasEvidenceTypeList,

        bias = bias,
        weight = weight,
        weightingConsiderationDescription = weightingConsiderationDescription,
        weightingType = weightingType,

        isDerivedFrom = isDerivedFrom,
        hasRequirement = hasRequirement,
        isRequirementOf = isRequirementOf,
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies,
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies,
        order = order,
        properties = properties,
    )
}

class InformationRequirementBuilder : RequirementBuilder<InformationRequirement>,
    AbstractRequirementBuilder<InformationRequirement>() {
    override fun build() = InformationRequirement(
        description = description,
        identifier = identifier,
        name = name,
        type = type,
        hasConcept = hasConcept,
        hasEvidenceTypeList = hasEvidenceTypeList,
        isDerivedFrom = isDerivedFrom,
        hasRequirement = hasRequirement,
        isRequirementOf = isRequirementOf,
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies,
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies,
        order = order,
        properties = properties,
    )
}

class ConstraintBuilder: RequirementBuilder<Constraint>, AbstractRequirementBuilder<Constraint>() {
    override fun build() = Constraint(
        description = description,
        identifier = identifier,
        name = name,
        type = type,
        hasConcept = hasConcept,
        hasEvidenceTypeList = hasEvidenceTypeList,
        isDerivedFrom = isDerivedFrom,
        hasRequirement = hasRequirement,
        isRequirementOf = isRequirementOf,
        enablingCondition = enablingCondition,
        enablingConditionDependencies = enablingConditionDependencies,
        required = required,
        validatingCondition = validatingCondition,
        validatingConditionDependencies = validatingConditionDependencies,
        order = order,
        properties = properties,
    )
}

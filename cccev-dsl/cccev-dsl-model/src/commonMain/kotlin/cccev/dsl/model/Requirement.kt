package cccev.dsl.model

import cccev.dsl.model.builder.ConstraintBuilder
import cccev.dsl.model.builder.CriterionBuilder
import cccev.dsl.model.builder.InformationRequirementBuilder
import kotlinx.serialization.Serializable

/**
 * The unique identifier of the requirement.
 * @visual json "TheRequirement"
 * @title DSL/RequirementId
 * @d2 model
 */
typealias RequirementIdentifier = String

/**
 * The unique id of the requirement.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/RequirementId
 * @d2 model
 */
typealias RequirementId = String

sealed interface Requirement {
    val description: String?
    val identifier: RequirementIdentifier
    val isDerivedFrom: List<ReferenceFramework>?
    val name: String?
    val type: Code?
    val kind: String
    val hasRequirement: List<Requirement>?
    var isRequirementOf: List<Requirement>?
    val hasConcept: List<InformationConcept>?
    val hasEvidenceTypeList: List<EvidenceTypeListBase>?
    val enablingCondition: String?
    val enablingConditionDependencies: List<InformationConceptIdentifier>
    val required: Boolean
    val validatingCondition: String?
    val validatingConditionDependencies: List<InformationConceptIdentifier>
    val order: Int?
    val properties: Map<String, String>?
}

@Serializable
open class Criterion(
    override val description: String? = null,
    override val identifier: RequirementIdentifier,
    override val name: String? = null,
    override val type: Code? = null,
    val bias: Double? = null,
    val weight: Double? = null,
    val weightingConsiderationDescription: String? = null,
    val weightingType: Code? = null,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>,
    override val order: Int?,
    override val properties: Map<String, String>?,
) : Requirement {
    override val kind: String = "CRITERION"
    override fun toString(): String {
        return "Criterion(" +
                "description=$description, " +
                "identifier=$identifier, " +
                "name=$name, " +
                "type=$type, " +
                "bias=$bias, " +
                "weight=$weight, " +
                "weightingConsiderationDescription=$weightingConsiderationDescription, " +
                "weightingType=$weightingType, " +
                "hasConcept=$hasConcept, " +
                "hasRequirement=$hasRequirement, " +
                "hasEvidenceTypeList=$hasEvidenceTypeList, " +
                "isDerivedFrom=$isDerivedFrom, " +
                "isRequirementOf=$isRequirementOf, " +
                ")"
    }
}

@Serializable
open class InformationRequirement(
    override val description: String? = null,
    override val identifier: RequirementIdentifier,
    override val name: String? = null,
    override val type: Code? = null,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>,
    override val order: Int?,
    override val properties: Map<String, String>?,
) : Requirement {
    override val kind: String = "INFORMATION"
    override fun toString(): String {
        return "InformationRequirement(" +
                "description=$description, " +
                "identifier=$identifier, " +
                "name=$name, " +
                "type=$type, " +
                "hasConcept=$hasConcept, " +
                "hasRequirement=$hasRequirement, " +
                "hasEvidenceTypeList=$hasEvidenceTypeList, " +
                "isDerivedFrom=$isDerivedFrom, " +
                "isRequirementOf=$isRequirementOf, " +
                ")"
    }
}

@Serializable
open class Constraint(
    override val description: String? = null,
    override val identifier: RequirementIdentifier,
    override val name: String? = null,
    override val type: Code? = null,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>,
    override val order: Int?,
    override val properties: Map<String, String>?,
) : Requirement {
    override val kind: String = "CONSTRAINT"
    override fun toString(): String {
        return "Constraint(" +
                "description=$description, " +
                "identifier=$identifier, " +
                "name=$name, " +
                "type=$type, " +
                "hasConcept=$hasConcept, " +
                "hasRequirement=$hasRequirement, " +
                "hasEvidenceTypeList=$hasEvidenceTypeList, " +
                "isDerivedFrom=$isDerivedFrom, " +
                "isRequirementOf=$isRequirementOf, " +
                ")"
    }
}

@Serializable
open class RequirementRef(
    override val identifier: RequirementIdentifier
): Requirement {
    override val description: String? = null
    override val isDerivedFrom: List<ReferenceFramework>? = null
    override val name: String? = null
    override val type: Code? = null
    override val hasRequirement: List<Requirement>? = null
    override var isRequirementOf: List<Requirement>? = null
    override val hasConcept: List<InformationConcept>? = null
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = null
    override val enablingCondition: String? = null
    override val enablingConditionDependencies: List<InformationConceptIdentifier> = emptyList()
    override val required: Boolean = true
    override val validatingCondition: String? = null
    override val validatingConditionDependencies: List<InformationConceptIdentifier> = emptyList()
    override val order: Int? = null
    override val properties: Map<String, String>? = null
    override val kind: String = "REFERENCE"
}

/**
 * Custom requirement which is considered validated when at least K of its N sub-requirements are met
 * where K is defined by the property [minRequirementsToMeet] and N is the size of the list [hasRequirement]
 * TODO more meaningful name
 */
open class PartialRequirement(
    override val identifier: RequirementIdentifier,
    override val description: String?,
    override val name: String?,
    override val type: Code?,
    val minRequirementsToMeet: Int,
    override val hasConcept: List<InformationConceptBase>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>,
    override val order: Int?,
    override val properties: Map<String, String>?,
): Requirement {
    override val kind: String = "PARTIAL"
}

fun criterion(init: CriterionBuilder.() -> Unit): Criterion = CriterionBuilder().apply(init).build()

fun informationRequirement(init: InformationRequirementBuilder.() -> Unit): InformationRequirement =
    InformationRequirementBuilder().apply(init).build()

fun constraint(init: ConstraintBuilder.() -> Unit): Constraint = ConstraintBuilder().apply(init).build()

fun requirementRef(identifier: RequirementIdentifier) = RequirementRef(identifier)

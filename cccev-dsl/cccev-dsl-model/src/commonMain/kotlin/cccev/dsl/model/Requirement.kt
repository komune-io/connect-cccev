package cccev.dsl.model

import cccev.dsl.model.builder.ConstraintBuilder
import cccev.dsl.model.builder.CriterionBuilder
import cccev.dsl.model.builder.InformationRequirementBuilder
import kotlinx.serialization.Serializable

/**
 * The unique identifier of the requirement.
 * @example "TheRequirement"
 * @title DSL/RequirementIdentifier
 * @d2 hidden
 */
typealias RequirementIdentifier = String

/**
 * The unique id of the requirement.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/RequirementId
 * @d2 hidden
 */
typealias RequirementId = String


/**
 * A Requirement.
 * @d2 model
 * @title DSL/Requirement
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 */
sealed interface Requirement {
    /**
     * The unique identifier for a specific requirement.
     * This variable is used to distinguish and reference
     * individual requirements within a system, ensuring
     * that each requirement can be uniquely identified and
     * accessed.
     */
    val id: RequirementId
    /**
     * A unique identifier used to represent a specific requirement in the system.
     * This identifier is utilized to track, reference, and manage requirements throughout the application's lifecycle.
     */
    val identifier: RequirementIdentifier
    /**
     * Description of the item or entity. This variable may hold a brief text
     * that explains or provides more information about the item, entity,
     * or process it is associated with.
     *
     * The variable is nullable, which means it can hold a null value
     * indicating the absence of a description.
     */
    val description: String?
    /**
     * List of reference frameworks from which this requirement is derived.
     * Each item in the list represents a framework providing context or standards related to the requirement.
     * Can be null if no reference frameworks are specified.
     */
    val isDerivedFrom: List<ReferenceFramework>?
    /**
     * The name of an entity which can be null.
     * Represents the identification label for the entity,
     * such as a user or an item, and may be absent in some cases.
     */
    val name: String?
    /**
     * Holds information about the type of a variable or object.
     * This can be `null` if the type is not specified or unknown.
     */
    val type: String?
    /**
     * A string specifying the kind or category of the requirement.
     */
    val kind: String
    /**
     * @example null
     */
    val hasRequirement: List<Requirement>?
    /**
     * Represents a list of requirements that are dependent on this specific requirement.
     * @example null
     */
    var isRequirementOf: List<Requirement>?
    /**
     * Represents a list of InformationConceptDTO objects related to the requirement.
     * Can be null if there are no related InformationConceptDTOs.
     */
    val hasConcept: List<InformationConceptDTO>?
    /**
     * A list of `EvidenceTypeList` associated with the requirement, specifying the types of evidence
     * that are relevant or necessary to meet the requirement.
     * The list may be null if no specific types of evidence are required.
     */
    val hasEvidenceTypeList: List<EvidenceTypeList>?
    /**
     * Represents the condition that enables a particular requirement. This condition must be satisfied
     * for the requirement to be considered active or valid. The value is a string that can describe
     * the condition, or it can be null if there is no specific enabling condition.
     */
    val enablingCondition: String?
    /**
     * A list of identifiers representing the dependencies required to enable a certain condition.
     *
     * This property holds the identifiers of the information concepts that must be present or valid
     * for the enabling condition of the requirement to be considered met. It is used in the context
     * of requirements or rules where certain conditions need to be satisfied based on external
     * dependencies or data points.
     *
     * @see InformationConceptIdentifier
     */
    val enablingConditionDependencies: List<InformationConceptIdentifier>?
    /**
     * Indicates whether the requirement is mandatory.
     */
    val required: Boolean
    /**
     * Represents a condition that must be validated for the requirement.
     * This variable stores a string that details the specific condition
     * that needs to be met. It can be null if no validating condition
     * is specified.
     */
    val validatingCondition: String?
    /**
     * A condition used to validate evidence in a given process.
     * This variable holds a string that represents a specific condition
     * or criteria that evidence must meet to be considered valid.
     * It is optional and may be null if no specific condition is set.
     */
    val evidenceValidatingCondition: String?
    /**
     * Represents a list of dependencies required for validating conditions.
     *
     * The list contains identifiers of information concepts that are necessary to validate specific conditions.
     * It can be null if no dependencies are defined.
     */
    val validatingConditionDependencies: List<InformationConceptIdentifier>?
    /**
     * Represents the order of the requirement within a sequence.
     * This value is nullable and can be used to prioritize or arrange requirements.
     */
    val order: Int?
    /**
     * A map containing properties related to the requirement.
     * Keys are property names and values are property values.
     * Can be null if no properties are defined.
     */
    val properties: Map<String, String>?
}


interface CriterionDTO: Requirement{
    val bias: Double?
    val weight: Double?
    val weightingConsiderationDescription: String?
    val weightingType: Code?
}

/**
 * A Criterion.
 * @d2 model
 * @title DSL/Criterion
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 */
@Serializable
open class Criterion(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
    override val description: String? = null,
    override val name: String? = null,
    override val type: String? = null,
    override val bias: Double? = null,
    override val weight: Double? = null,
    override val weightingConsiderationDescription: String? = null,
    override val weightingType: Code? = null,
    override val hasConcept: List<InformationConceptDTO>? = null,
    override val hasRequirement: List<Requirement>? = null,
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = null,
    override val isDerivedFrom: List<ReferenceFramework>? = null,
    override var isRequirementOf: List<Requirement>? = null,
    override val enablingCondition: String? = null,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val required: Boolean,
    override val validatingCondition: String? = null,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val order: Int? = null,
    override val properties: Map<String, String>? = null,
    override val evidenceValidatingCondition: String? = null,
) : CriterionDTO {
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

/**
 * An InformationRequirement.
 * @d2 model
 * @title DSL/InformationRequirement
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 */
@Serializable
open class InformationRequirement(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
    override val description: String? = null,
    override val name: String? = null,
    override val type: String? = null,
    override val hasConcept: List<InformationConceptDTO>? = null,
    override val hasRequirement: List<Requirement>? = null,
    override val hasEvidenceTypeList: List<EvidenceTypeList>? = null,
    override val isDerivedFrom: List<ReferenceFramework>? = null,
    override var isRequirementOf: List<Requirement>? = null,
    override val enablingCondition: String? = null,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val required: Boolean,
    override val validatingCondition: String? = null,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val order: Int? = null,
    override val properties: Map<String, String>? = null,
    override val evidenceValidatingCondition: String? = null,
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

/**
 * An Constraint.
 * @d2 model
 * @title DSL/Constraint
 * @parent [cccev.dsl.model.d2.D2RequirementPage]
 */
@Serializable
open class Constraint(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
    override val description: String? = null,
    override val name: String? = null,
    override val type: String? = null,
    override val hasConcept: List<InformationConceptDTO>? = null,
    override val hasRequirement: List<Requirement>? = null,
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = null,
    override val isDerivedFrom: List<ReferenceFramework>? = null,
    override var isRequirementOf: List<Requirement>? = null,
    override val enablingCondition: String?,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val required: Boolean,
    override val validatingCondition: String?,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val order: Int?,
    override val properties: Map<String, String>?,
    override val evidenceValidatingCondition: String?,
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
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
): Requirement {
    override val description: String? = null
    override val isDerivedFrom: List<ReferenceFramework>? = null
    override val name: String? = null
    override val type: String? = null
    override val hasRequirement: List<Requirement>? = null
    override var isRequirementOf: List<Requirement>? = null
    override val hasConcept: List<InformationConceptDTO>? = null
    override val hasEvidenceTypeList: List<EvidenceTypeList>? = null
    override val enablingCondition: String? = null
    override val enablingConditionDependencies: List<InformationConceptIdentifier>? = null
    override val required: Boolean = true
    override val validatingCondition: String? = null
    override val validatingConditionDependencies: List<InformationConceptIdentifier>? = null
    override val order: Int? = null
    override val properties: Map<String, String>? = null
    override val kind: String = "REFERENCE"
    override val evidenceValidatingCondition: String? = null
}

/**
 * Custom requirement which is considered validated when at least K of its N sub-requirements are met
 * where K is defined by the property [minRequirementsToMeet] and N is the size of the list [hasRequirement]
 * TODO more meaningful name
 */
open class PartialRequirement(
    override val id: RequirementId,
    override val identifier: RequirementIdentifier,
    override val description: String?,
    override val name: String? = null,
    override val type: String? = null,
    val minRequirementsToMeet: Int,
    override val hasConcept: List<InformationConcept>? = null,
    override val hasRequirement: List<Requirement>? = null,
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = null,
    override val isDerivedFrom: List<ReferenceFramework>? = null,
    override var isRequirementOf: List<Requirement>? = null,
    override val enablingCondition: String? = null,
    override val enablingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val required: Boolean,
    override val validatingCondition: String? = null,
    override val validatingConditionDependencies: List<InformationConceptIdentifier>? = null,
    override val order: Int? = null,
    override val properties: Map<String, String>? = null,
    override val evidenceValidatingCondition: String? = null,
): Requirement {
    override val kind: String = "PARTIAL"
}

fun criterion(init: CriterionBuilder.() -> Unit): Criterion = CriterionBuilder().apply(init).build()

fun informationRequirement(init: InformationRequirementBuilder.() -> Unit): InformationRequirement =
    InformationRequirementBuilder().apply(init).build()

fun constraint(init: ConstraintBuilder.() -> Unit): Constraint = ConstraintBuilder().apply(init).build()

fun requirementRef(id: RequirementId, identifier: RequirementIdentifier) = RequirementRef(id, identifier)

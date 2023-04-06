package cccev.dsl.model

import cccev.dsl.model.builder.ConstraintBuilder
import cccev.dsl.model.builder.CriterionBuilder
import cccev.dsl.model.builder.InformationRequirementBuilder
import kotlinx.serialization.Serializable

/**
 * The unique id of the requirement.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/RequirementId
 * @d2 model
 */
typealias RequirementId = String

sealed interface Requirement {
    val description: String?
    val identifier: RequirementId?
    val isDerivedFrom: List<ReferenceFramework>?
    val name: String?
    val type: Code?
    val hasRequirement: List<Requirement>?
    var isRequirementOf: List<Requirement>?
    var hasQualifiedRelation: List<Requirement>?

    val hasConcept: List<InformationConcept>?
    val hasEvidenceTypeList: List<EvidenceTypeListBase>?
}

@Serializable
open class Criterion(
    override val description: String? = null,
    override val identifier: RequirementId?,
    override val name: String?,
    override val type: Code?,
    val bias: Double? = null,
    val weight: Double? = null,
    val weightingConsiderationDescription: String? = null,
    val weightingType: Code? = null,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override var hasQualifiedRelation: List<Requirement>? = emptyList(),
) : Requirement {
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
                "hasQualifiedRelation=$hasQualifiedRelation" +
                ")"
    }
}

@Serializable
open class InformationRequirement(
    override val description: String?,
    override val identifier: RequirementId?,
    override val name: String?,
    override val type: Code?,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override var hasQualifiedRelation: List<Requirement>? = emptyList(),
) : Requirement {
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
                "hasQualifiedRelation=$hasQualifiedRelation" +
                ")"
    }
}

@Serializable
open class Constraint(
    override val description: String?,
    override val identifier: RequirementId?,
    override val name: String?,
    override val type: Code?,
    override val hasConcept: List<InformationConcept>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override var hasQualifiedRelation: List<Requirement>? = emptyList(),
) : Requirement {
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
                "hasQualifiedRelation=$hasQualifiedRelation" +
                ")"
    }
}

/**
 * Custom requirement which is considered validated when at least K of its N sub-requirements are met
 * where K is defined by the property [minRequirementsToMeet] and N is the size of the list [hasRequirement]
 * TODO more meaningful name
 */
open class PartialRequirement(
    override val description: String?,
    override val identifier: RequirementId?,
    override val name: String?,
    override val type: Code?,
    val minRequirementsToMeet: Int,
    override val hasConcept: List<InformationConceptBase>? = emptyList(),
    override val hasRequirement: List<Requirement>? = emptyList(),
    override val hasEvidenceTypeList: List<EvidenceTypeListBase>? = emptyList(),
    override val isDerivedFrom: List<ReferenceFramework>? = emptyList(),
    override var isRequirementOf: List<Requirement>? = emptyList(),
    override var hasQualifiedRelation: List<Requirement>? = emptyList(),
) : Requirement

fun criterion(init: CriterionBuilder.() -> Unit): Criterion = CriterionBuilder().apply(init).build()

fun informationRequirement(init: InformationRequirementBuilder.() -> Unit): InformationRequirement =
    InformationRequirementBuilder().apply(init).build()

fun constraint(init: ConstraintBuilder.() -> Unit): Constraint = ConstraintBuilder().apply(init).build()

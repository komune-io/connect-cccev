package cccev.dsl.model.builder

import cccev.dsl.model.EvidenceTypeListBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.ReferenceFramework
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementIdentifier
import kotlinx.datetime.Clock

interface RequirementBuilder<T : Requirement> {
    var identifier: RequirementIdentifier
    var description: String?
    var evidenceValidatingCondition: String?
    var name: String?
    var type: String?

    var hasConcept: MutableList<InformationConcept>
    var hasEvidenceTypeList: List<EvidenceTypeListBase>?

    var enablingCondition: String?
    var enablingConditionDependencies: List<InformationConcept>
    var required: Boolean
    var validatingCondition: String?
    var validatingConditionDependencies: List<InformationConcept>
    var order: Int?
    var properties: Map<String, String>?

    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    fun criterion(init: CriterionBuilder.() -> Unit)
    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    fun informationRequirement(init: InformationRequirementBuilder.() -> Unit)
    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    fun constraint(init: ConstraintBuilder.() -> Unit)
    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    operator fun Requirement.unaryPlus()

    fun isRequirementOf(lambda: RequirementsLinkedBuilder.() -> Unit)
    fun hasRequirement(lambda: RequirementsLinkedBuilder.() -> Unit)
    fun hasQualifiedRelation(relation: String, lambda: RequirementsLinkedBuilder.() -> Unit)
    fun isDerivedFrom(lambda: ReferenceFrameworkListBuilder.() -> Unit)
    fun hasConcept(lambda: InformationConceptListBuilder.() -> Unit)
    fun build(): Requirement
}
abstract class AbstractRequirementBuilder<T : Requirement> : RequirementBuilder<T> {
    override var evidenceValidatingCondition: String? = null
    override var description: String? = null
    override var identifier: RequirementIdentifier = "req_${Clock.System.now().toEpochMilliseconds()}"
    override var name: String? = null
    override var type: String? = null
    override var hasConcept: MutableList<InformationConcept> = mutableListOf()
    override var hasEvidenceTypeList: List<EvidenceTypeListBase>? = null

    protected var isDerivedFrom = mutableListOf<ReferenceFramework>()
    protected var isRequirementOf = mutableListOf<Requirement>()
    protected var hasRequirement = mutableListOf<Requirement>()
    protected var hasQualifiedRelation = mutableMapOf<String, List<Requirement>>()

    override var enablingCondition: String? = null
    override var enablingConditionDependencies: List<InformationConcept> = mutableListOf()
    override var required: Boolean = true
    override var validatingCondition: String? = null
    override var validatingConditionDependencies: List<InformationConcept> = mutableListOf()
    override var order: Int? = null
    override var properties: Map<String, String>? = null

    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    override fun criterion(
        init: CriterionBuilder.() -> Unit,
    ) {
        +CriterionBuilder().apply(init).build()
    }

    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    override fun informationRequirement(
        init: InformationRequirementBuilder.() -> Unit
    ) {
        +InformationRequirementBuilder().apply(init).build()
    }

    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    override fun constraint(
        init: ConstraintBuilder.() -> Unit) {
        +ConstraintBuilder().apply(init).build()
    }

    @Deprecated("Use isRequirementOf, hasRequirement or hasQualifiedRelation")
    override operator fun Requirement.unaryPlus() {
        this@AbstractRequirementBuilder.hasRequirement.add(this)
    }

    override fun isRequirementOf(lambda: RequirementsLinkedBuilder.() -> Unit) {
        val list = RequirementsLinkedBuilder().apply(lambda).build()
        isRequirementOf.addAll(list)
    }

    override fun hasRequirement(lambda: RequirementsLinkedBuilder.() -> Unit) {
        val list = RequirementsLinkedBuilder().apply(lambda).build()
        hasRequirement.addAll(list)
    }

    override fun hasQualifiedRelation(relation: String, lambda: RequirementsLinkedBuilder.() -> Unit) {
        val list = RequirementsLinkedBuilder().apply(lambda).build()
        hasQualifiedRelation[relation] = hasQualifiedRelation.getOrElse(relation) { emptyList() } + list
    }

    override fun isDerivedFrom(lambda: ReferenceFrameworkListBuilder.() -> Unit) {
        val list = ReferenceFrameworkListBuilder().apply(lambda).build()
        isDerivedFrom.addAll(list)
    }

    override fun hasConcept(lambda: InformationConceptListBuilder.() -> Unit) {
        val list = InformationConceptListBuilder().apply(lambda).build()
        hasConcept.addAll(list)
    }
}

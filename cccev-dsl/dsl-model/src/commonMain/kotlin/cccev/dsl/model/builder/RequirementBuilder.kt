package cccev.dsl.model.builder

import cccev.dsl.model.Code
import cccev.dsl.model.EvidenceTypeListBase
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.ReferenceFramework
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementId

interface RequirementBuilder<T : Requirement> {
    var description: String?
    var identifier: RequirementId?
    var name: String?
    var type: Code?

    var hasConcept: MutableList<InformationConcept>
    var hasEvidenceTypeList: List<EvidenceTypeListBase>?

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
    fun hasQualifiedRelation(lambda: RequirementsLinkedBuilder.() -> Unit)
    fun isDerivedFrom(lambda: ReferenceFrameworkListBuilder.() -> Unit)
    fun hasConcept(lambda: InformationConceptListBuilder.() -> Unit)
    fun build(): Requirement
}
abstract class AbstractRequirementBuilder<T : Requirement> : RequirementBuilder<T> {
    override var description: String? = null
    override var identifier: RequirementId? = null
    override var name: String? = null
    override var type: Code? = null
    override var hasConcept: MutableList<InformationConcept> = mutableListOf()
    override var hasEvidenceTypeList: List<EvidenceTypeListBase>? = null

    protected var isDerivedFrom = mutableListOf<ReferenceFramework>()
    protected var isRequirementOf = mutableListOf<Requirement>()
    protected var hasRequirement = mutableListOf<Requirement>()
    protected var hasQualifiedRelation = mutableListOf<Requirement>()

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
    override fun hasQualifiedRelation(lambda: RequirementsLinkedBuilder.() -> Unit) {
        val list = RequirementsLinkedBuilder().apply(lambda).build()
        hasQualifiedRelation.addAll(list)
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

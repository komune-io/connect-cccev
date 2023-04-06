package cccev.dsl.model.builder

import cccev.dsl.model.Code
import cccev.dsl.model.Criterion
import cccev.dsl.model.DataUnit
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.InformationConceptId

class InformationConceptListBuilder {

    private var informationConcepts = mutableListOf<InformationConcept>()

    operator fun InformationConcept.unaryPlus() {
        informationConcepts.add(this)
    }

    operator fun InformationConceptBuilder.unaryPlus() {
        informationConcepts.add(this.build())
    }
    fun build(): List<InformationConcept> = informationConcepts.toList()
}
class InformationConceptBuilder {
    var identifier: InformationConceptId? = null
    var name: String? = null
    var unit: DataUnit? = null
    var type: Code? = null
    var description: String? = null
    var expressionOfExpectedValue: String? = null

    fun build() = InformationConceptBase(
        identifier = identifier!!,
        name = name!!,
        unit = unit,
        type = type,
        description = description,
        expressionOfExpectedValue = expressionOfExpectedValue
    )
}

fun informationConcept(init: InformationConceptBuilder.() -> Unit): InformationConcept
    = InformationConceptBuilder().apply(init).build()

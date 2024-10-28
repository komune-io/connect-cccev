package cccev.dsl.model.builder

import cccev.dsl.model.Code
import cccev.dsl.model.DataUnit
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptBase
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.InformationConceptRef
import cccev.dsl.model.XSDString
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4

class InformationConceptListBuilder {

    var informationConcepts = mutableListOf<InformationConcept>()

    operator fun InformationConcept.unaryPlus() {
        informationConcepts.add(this)
    }

    operator fun InformationConceptBuilder.unaryPlus() {
        informationConcepts.add(this.build())
    }

    operator fun List<InformationConcept>.unaryPlus() {
        informationConcepts.addAll(this)
    }

    fun build(): List<InformationConcept> = informationConcepts.toList()
}

class InformationConceptBuilder {
    var id: InformationConceptId? = null
    var identifier: InformationConceptIdentifier? = null
    var name: String? = null
    var unit: DataUnit = XSDString
    var type: Code? = null
    var description: String? = null
    var expressionOfExpectedValue: String? = null

    fun build() = InformationConceptBase(
        id = id ?: uuid4().toString(),
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

fun informationConceptRef(id: InformationConceptId, identifier: InformationConceptIdentifier) = InformationConceptRef(id, identifier)

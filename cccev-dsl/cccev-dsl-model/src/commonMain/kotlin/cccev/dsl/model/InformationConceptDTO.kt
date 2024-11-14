package cccev.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias InformationConceptId = String
typealias InformationConceptIdentifier = String
/**
 * The InformationConcept defines a type of information that can be collected.
 * @d2 model
 * @parent [cccev.dsl.model.D2DslModelPage]
 */
@JsExport
@JsName("InformationConceptDTO")
interface InformationConceptDTO {
    //TODO FIND A WAY TO REMOVE All Id from the model dsl
	val id: InformationConceptId
	val identifier: InformationConceptIdentifier
	val name: String
	val unit: DataUnitDTO
	val type: Code?
	val description: String?
	val expressionOfExpectedValue: String?
	val dependsOn: List<InformationConceptId>?
}

@Serializable
open class InformationConcept(
    override val id: InformationConceptId,
    override val identifier: InformationConceptIdentifier,
    override val name: String,
    override val unit: DataUnit,
    override val type: Code? = null,
    override val description: String? = null,
    override val expressionOfExpectedValue: String? = null,
    override val dependsOn: List<InformationConceptId>? = null
): InformationConceptDTO

@Serializable
class InformationConceptRef(
    override val id: InformationConceptId,
    override val identifier: InformationConceptIdentifier
): InformationConceptDTO {
    override val name: String = ""
    override val unit: DataUnit = XSDString
    override val type: Code? = null
    override val description: String? = null
    override val expressionOfExpectedValue: String? = null
    override val dependsOn: List<InformationConceptId>? = null
}

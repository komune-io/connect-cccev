package cccev.f2.concept.model

import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 model
 * @parent [cccev.f2.concept.D2InformationConceptPage]
 * @order 10
 */
@JsExport
interface InformationConceptFlatDTO {
    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.id]
     */
    val id: InformationConceptId

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.identifier]
     */
    val identifier: InformationConceptIdentifier

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.name]
     */
    val name: String

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.unit]
     */
    val unitIdentifier: DataUnitIdentifier

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.description]
     */
    val description: String?

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.expressionOfExpectedValue]
     */
    val expressionOfExpectedValue: String?

    /**
     * @ref [cccev.dsl.model.InformationConceptDTO.dependsOn]
     */
    val dependsOn: List<InformationConceptIdentifier>?
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptFlat(
    override val id: InformationConceptId,
    override val identifier: InformationConceptIdentifier,
    override val name: String,
    override val unitIdentifier: DataUnitIdentifier,
    override val description: String?,
    override val expressionOfExpectedValue: String?,
    override val dependsOn: List<InformationConceptIdentifier>?
): InformationConceptFlatDTO

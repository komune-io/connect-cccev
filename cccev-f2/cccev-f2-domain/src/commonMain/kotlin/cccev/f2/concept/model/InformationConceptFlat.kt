package cccev.f2.concept.model

import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 model
 * @parent [cccev.core.concept.D2InformationConceptPage]
 * @order 10
 */
@JsExport
interface InformationConceptFlatDTO {
    /**
     * @ref [InformationConceptDTO.id]
     */
    val id: InformationConceptId

    /**
     * @ref [InformationConceptDTO.identifier]
     */
    val identifier: InformationConceptIdentifier

    /**
     * @ref [InformationConceptDTO.name]
     */
    val name: String

    /**
     * @ref [InformationConceptDTO.unit]
     */
    val unitIdentifier: DataUnitIdentifier

    /**
     * @ref [InformationConceptDTO.description]
     */
    val description: String?

    /**
     * @ref [InformationConceptDTO.expressionOfExpectedValue]
     */
    val expressionOfExpectedValue: String?

    /**
     * @ref [InformationConceptDTO.dependsOn]
     */
    val dependsOn: List<InformationConceptIdentifier>
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
    override val dependsOn: List<InformationConceptIdentifier>
): InformationConceptFlatDTO

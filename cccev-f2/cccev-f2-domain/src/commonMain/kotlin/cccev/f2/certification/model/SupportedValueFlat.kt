package cccev.f2.certification.model

import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.SupportedValueId
import cccev.dsl.model.SupportedValueIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * TODO
 * @d2 model
 * @parent [cccev.dsl.model.d2.D2CertificationPage]
 * @order 30
 */
@JsExport
interface SupportedValueFlatDTO {
    val id: SupportedValueId
    val identifier: SupportedValueIdentifier
    val value: String?
    val conceptIdentifier: InformationConceptIdentifier
}

@Serializable
data class SupportedValueFlat(
    override val id: SupportedValueId,
    override val identifier: SupportedValueIdentifier,
    override val value: String?,
    override val conceptIdentifier: InformationConceptIdentifier
): SupportedValueFlatDTO

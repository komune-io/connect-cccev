package cccev.f2.certification.model

import cccev.core.certification.model.SupportedValueId
import cccev.core.concept.model.InformationConceptIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * TODO
 * @d2 model
 * @parent [cccev.f2.certification.domain.D2CertificationApiPage]
 * @order 30
 */
@JsExport
interface SupportedValueFlatDTO {
    val id: SupportedValueId
    val value: String?
    val conceptIdentifier: InformationConceptIdentifier
}

@Serializable
data class SupportedValueFlat(
    override val id: SupportedValueId,
    override val value: String?,
    override val conceptIdentifier: InformationConceptIdentifier
): SupportedValueFlatDTO

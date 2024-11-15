package cccev.f2.certification.model

import cccev.dsl.model.CertificationId
import cccev.dsl.model.RequirementCertificationId
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * TODO
 * @d2 model
 * @parent [cccev.dsl.model.d2.D2CertificationPage]
 * @order 10
 */
@JsExport
interface CertificationFlatDTO {
    val id: CertificationId
    val requirementCertificationIds: List<RequirementCertificationId>
}

@Serializable
data class CertificationFlat(
    override val id: CertificationId,
    override val requirementCertificationIds: List<RequirementCertificationId>
): CertificationFlatDTO

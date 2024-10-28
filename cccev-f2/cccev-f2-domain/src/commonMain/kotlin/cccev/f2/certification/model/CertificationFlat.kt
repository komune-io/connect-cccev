package cccev.f2.certification.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * TODO
 * @d2 model
 * @parent [cccev.f2.certification.domain.D2CertificationApiPage]
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

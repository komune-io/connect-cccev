package cccev.f2.certification.domain.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
class Certification(
    val id: String,
    val requirementCertifications: List<RequirementCertification>
)

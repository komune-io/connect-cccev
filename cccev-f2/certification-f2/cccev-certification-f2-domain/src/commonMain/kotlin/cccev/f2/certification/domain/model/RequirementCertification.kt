package cccev.f2.certification.domain.model

import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementCertificationId
import cccev.dsl.model.SupportedValue
import kotlinx.serialization.Serializable

@Serializable
class RequirementCertification(
    val id: RequirementCertificationId,
    val requirement: Requirement,
    val subCertifications: List<RequirementCertification>,
    val values: List<SupportedValue>,
    val isEnabled: Boolean,
    val isValidated: Boolean,
    val hasAllValues: Boolean,
    val isFulfilled: Boolean,
)

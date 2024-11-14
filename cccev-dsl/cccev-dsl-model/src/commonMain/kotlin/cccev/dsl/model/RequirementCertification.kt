package cccev.dsl.model

import kotlinx.serialization.Serializable

/**
 * The unique id of the certificate.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/CertificationId
 * @d2 model
 * @parent [cccev.dsl.model.D2DslModelPage]
 */
typealias RequirementCertificationId = String

@Serializable
class RequirementCertification(
    val id: RequirementCertificationId,
    val requirement: Requirement,
    val subCertifications: List<RequirementCertification>,
    val values: List<SupportedValue>,
    val isEnabled: Boolean = false,
    val isValidated: Boolean = false,
    val hasAllValues: Boolean = false,
    val isFulfilled: Boolean = false

)

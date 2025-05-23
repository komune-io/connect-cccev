package cccev.dsl.model

import kotlinx.serialization.Serializable

/**
 * The unique id of the certificate.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/RequirementCertification
 * @d2 hidden
 */
typealias RequirementCertificationId = String

/**
 * The unique id of the certificate.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/RequirementCertification
 * @parent [cccev.dsl.model.d2.D2CertificationPage]
 */
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

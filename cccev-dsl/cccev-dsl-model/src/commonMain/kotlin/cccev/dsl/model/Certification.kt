package cccev.dsl.model

import kotlinx.serialization.Serializable

/**
 * The unique id of the certificate.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/CertificationId
 *
 * @d2 hidden
 */
typealias CertificationId = String

/**
 * A certification is a set of requirements that must be met to be certified.
 * @title Certification
 * @d2 model
 * @parent [cccev.dsl.model.d2.CertificationPage]
 */
@Serializable
data class Certification(
    val id: CertificationId,
    val requirementCertifications: List<RequirementCertification> = mutableListOf()
)

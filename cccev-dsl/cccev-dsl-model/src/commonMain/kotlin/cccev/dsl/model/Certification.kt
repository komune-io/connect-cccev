package cccev.dsl.model

import kotlinx.serialization.Serializable


/**
 * The unique id of the certificate.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @title DSL/CertificationId
 * @d2 model
 */
typealias CertificationId = String

@Serializable
data class Certification(
    val id: String,
    val requirementCertifications: MutableList<RequirementCertification> = mutableListOf()
)
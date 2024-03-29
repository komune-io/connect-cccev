package cccev.core.certification.command

import cccev.core.certification.model.CertificationId
import cccev.core.certification.model.RequirementCertificationId
import cccev.s2.requirement.domain.model.RequirementIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * @d2 command
 */
@JsExport
@JsName("CertificationAddRequirementsCommandDTO")
interface CertificationAddRequirementsCommandDTO {
    /**
     * Id of the certification to add the requirements to.
     */
    val id: CertificationId

    /**
     * Optional RequirementCertification id in which to add the requirements.
     * If null, the requirements will be added to the root of the certification itself.
     */
    val parentId: RequirementCertificationId?

    /**
     * Identifiers of the requirements that the certifications will have to fulfill.
     */
    val requirementIdentifiers: List<RequirementIdentifier>
}

/**
 * @d2 inherit
 */
data class CertificationAddRequirementsCommand(
    override val id: CertificationId,
    override val parentId: RequirementCertificationId?,
    override val requirementIdentifiers: List<RequirementIdentifier>
): CertificationAddRequirementsCommandDTO

/**
 * @d2 event
 */
@JsExport
@JsName("CertificationAddedRequirementsEventDTO")
interface CertificationAddedRequirementsEventDTO {
    /**
     * Id of the certification the requirements have been added to.
     */
    val id: CertificationId

    /**
     * Optional RequirementCertification id in which the requirements have been added.
     * If null, the requirements have be added to the root of the certification itself.
     */
    val parentId: RequirementCertificationId?

    /**
     * List of the added requirement certifications.
     */
    val requirementCertificationIds: List<RequirementCertificationId>
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationAddedRequirementsEvent(
    override val id: CertificationId,
    override val parentId: RequirementCertificationId?,
    override val requirementCertificationIds: List<RequirementCertificationId>
): CertificationAddedRequirementsEventDTO

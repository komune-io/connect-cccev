package cccev.core.certification.command

import cccev.core.certification.model.CertificationId
import cccev.core.certification.model.RequirementCertificationId
import cccev.core.requirement.model.RequirementIdentifier
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Add requirements to fulfill in a certification.
 * @d2 function
 * @parent [cccev.f2.certification.domain.D2CertificationApiPage]
 * @order 20
 */
typealias CertificationAddRequirementsFunction = F2Function<CertificationAddRequirementsCommand, CertificationAddedRequirementsEvent>

/**
 * @d2 command
 * @parent [CertificationAddRequirementsFunction]
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
 * @parent [CertificationAddRequirementsFunction]
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

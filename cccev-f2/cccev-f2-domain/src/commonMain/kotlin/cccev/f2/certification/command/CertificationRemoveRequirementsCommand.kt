package cccev.f2.certification.command

import cccev.dsl.model.CertificationId
import cccev.dsl.model.RequirementId
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Remove requirements to fulfill from a certification.
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2CertificationPage]
 * @child [cccev.core.certification.command.CertificationRemoveRequirementsCommandDTO]
 * @child [cccev.core.certification.command.CertificationRemovedRequirementsEventDTO]
 * @order 21
 */
typealias CertificationRemoveRequirementsFunction
        = F2Function<CertificationRemoveRequirementsCommand, CertificationRemovedRequirementsEvent>

/**
 * @d2 command
 * @parent [CertificationRemoveRequirementsFunction]
 */
@JsExport
@JsName("CertificationRemoveRequirementsCommandDTO")
interface CertificationRemoveRequirementsCommandDTO {
    /**
     * Id of the certification to remove the requirements from.
     */
    val id: CertificationId

    /**
     * Ids of the requirements to remove from the certification.
     */
    val requirementIds: List<RequirementId>
}

/**
 * @d2 inherit
 */
data class CertificationRemoveRequirementsCommand(
    override val id: CertificationId,
    override val requirementIds: List<RequirementId>
): CertificationRemoveRequirementsCommandDTO

/**
 * @d2 event
 * @parent [CertificationRemoveRequirementsFunction]
 */
@JsExport
@JsName("CertificationRemovedRequirementsEventDTO")
interface CertificationRemovedRequirementsEventDTO {
    /**
     * Id of the certification the requirements have been removed from.
     */
    val id: CertificationId

    /**
     * Ids of the removed requirements
     */
    val requirementIds: List<RequirementId>
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationRemovedRequirementsEvent(
    override val id: CertificationId,
    override val requirementIds: List<RequirementId> = emptyList(),
): CertificationRemovedRequirementsEventDTO

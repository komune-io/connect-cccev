package cccev.f2.certification.command

import cccev.dsl.model.CertificationId
import cccev.dsl.model.RequirementIdentifier
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create a certification.
 * @d2 function
 * @parent [cccev.f2.certification.D2CertificationPage]
 * @order 10
 */
typealias CertificationCreateFunction = F2Function<CertificationCreateCommand, CertificationCreatedEvent>

/**
 * @d2 command
 * @parent [CertificationCreateFunction]
 */
@JsExport
@JsName("CertificationCreateCommandDTO")
interface CertificationCreateCommandDTO {
    /**
     * Custom unique identifier for the certification. If null, a random identifier will be generated.
     */
    val id: CertificationId?

    /**
     * Identifiers of the requirements that will be certified.
     */
    val requirementIdentifiers: List<RequirementIdentifier>
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationCreateCommand(
    override val id: CertificationId?,
    override val requirementIdentifiers: List<RequirementIdentifier> = emptyList()
): CertificationCreateCommandDTO

/**
 * @d2 event
 * @parent [CertificationCreateFunction]
 */
@JsExport
@JsName("CertificationCreatedEventDTO")
interface CertificationCreatedEventDTO {
    /**
     * ID of the certification as specified in the command, or random if not.
     */
    val id: CertificationId
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationCreatedEvent(
    override val id: CertificationId,
): CertificationCreatedEventDTO

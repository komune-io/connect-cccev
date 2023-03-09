package cccev.s2.evidence.domain.command.type

import cccev.s2.evidence.domain.D2EvidenceTypePage
import cccev.s2.evidence.domain.EvidenceTypeEvent
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeInitCommand
import cccev.s2.evidence.domain.EvidenceTypeState
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create a new evidence type.
 * @d2 function
 * @parent [D2EvidenceTypePage]
 * @order 10
 */
interface EvidenceTypeCreateFunction

@JsExport
@JsName("EvidenceTypeCreateCommandDTO")
interface EvidenceTypeCreateCommandDTO: EvidenceTypeInitCommand {
    /**
     * Name of the evidence type.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.name]
     */
    val name: String

    /**
     * Description of the evidence type.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.description]
     */
    val description: String

    /**
     * How long (in milliseconds) an evidence of this type is considered as valid after it has been issued, if applicable.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.validityPeriodConstraint]
     */
    val validityPeriodConstraint: Long?
}

/**
 * @d2 command
 * @parent [EvidenceTypeCreateFunction]
 */
@Serializable
data class EvidenceTypeCreateCommand(
    override val name: String,
    override val description: String,
    override val validityPeriodConstraint: Long?
): EvidenceTypeCreateCommandDTO

@JsExport
@JsName("EvidenceTypeCreatedEventDTO")
interface EvidenceTypeCreatedEventDTO: EvidenceTypeEvent {
    /**
     * Identifier of the created evidence type.
     */
    val id: EvidenceTypeId

    /**
     * Name of the evidence type.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.name]
     */
    val name: String

    /**
     * Description of the evidence type.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.description]
     */
    val description: String

    /**
     * How long (in milliseconds) an evidence of this type is considered as valid after it has been issued, if applicable.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.validityPeriodConstraint]
     */
    val validityPeriodConstraint: Long?

    val status: EvidenceTypeState
}

/**
 * @d2 event
 * @parent [EvidenceTypeCreateFunction]
 */
@Serializable
data class EvidenceTypeCreatedEvent(
    override val id: EvidenceTypeId,
    override val name: String,
    override val description: String,
    override val validityPeriodConstraint: Long?,
    override val status: EvidenceTypeState
): EvidenceTypeCreatedEventDTO {
    override fun s2Id() = id
}

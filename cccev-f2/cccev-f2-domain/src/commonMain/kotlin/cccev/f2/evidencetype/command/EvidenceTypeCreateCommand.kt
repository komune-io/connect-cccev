package cccev.f2.evidencetype.command

import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.EvidenceTypeIdentifier
import cccev.dsl.model.InformationConceptIdentifier
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create an evidence type
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
typealias EvidenceTypeCreateFunction = F2Function<EvidenceTypeCreateCommand, EvidenceTypeCreatedEvent>

/**
 * @d2 command
 * @parent [EvidenceTypeCreateFunction]
 */
@JsExport
@JsName("EvidenceTypeCreateCommandDTO")
interface EvidenceTypeCreateCommandDTO {
    /**
     * Custom unique id for the evidence type. If null, a random id will be generated.
     */
    val id: EvidenceTypeId?

    /**
     * Custom unique identifier for the evidence type. If null, a random id will be generated.
     */
    val identifier: EvidenceTypeIdentifier?

    /**
     * Name of the evidence type
     * @example "Weather report"
     */
    val name: String

    /**
     * Identifiers of the supported information concepts
     */
    val conceptIdentifiers: List<InformationConceptIdentifier>
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeCreateCommand(
    override val id: EvidenceTypeId? = null,
    override val identifier: EvidenceTypeIdentifier? = null,
    override val name: String,
    override val conceptIdentifiers: List<InformationConceptIdentifier> = emptyList()
): EvidenceTypeCreateCommandDTO

/**
 * @d2 event
 * @parent [EvidenceTypeCreateFunction]
 */
@JsExport
@JsName("EvidenceTypeCreatedEventDTO")
interface EvidenceTypeCreatedEventDTO {
    /**
     * Id of the evidence type as specified in the command, or random if not.
     */
    val id: EvidenceTypeId
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeCreatedEvent(
    override val id: EvidenceTypeId,
): EvidenceTypeCreatedEventDTO

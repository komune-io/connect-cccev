package cccev.core.evidencetype.command

import cccev.core.concept.model.InformationConceptIdentifier
import cccev.core.evidencetype.model.EvidenceTypeId
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Create an evidence type
 * @d2 function
 * @parent [D2EvidenceTypePage]
 */
typealias EvidenceTypeCreateFunction = F2Function<EvidenceTypeCreateCommand, EvidenceTypeCreatedEvent>

/**
 * @d2 command
 */
@JsExport
@JsName("EvidenceTypeCreateCommandDTO")
interface EvidenceTypeCreateCommandDTO {
    /**
     * Custom unique identifier for the evidence type. If null, a random id will be generated.
     */
    val id: EvidenceTypeId?

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
    override val name: String,
    override val conceptIdentifiers: List<InformationConceptIdentifier> = emptyList()
): EvidenceTypeCreateCommandDTO

/**
 * @d2 event
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

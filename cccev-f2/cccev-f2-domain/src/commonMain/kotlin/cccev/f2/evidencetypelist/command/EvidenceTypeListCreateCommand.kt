package cccev.f2.evidencetypelist.command

import cccev.dsl.model.EvidenceTypeListId
import cccev.dsl.model.EvidenceTypeListIdentifier
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Create an evidence type
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
typealias EvidenceTypeListCreateFunction = F2Function<EvidenceTypeListCreateCommand, EvidenceTypeListCreatedEvent>

/**
 * @d2 command
 * @parent [EvidenceTypeListCreateFunction]
 */
@JsExport
@JsName("EvidenceTypeListCreateCommandDTO")
interface EvidenceTypeListCreateCommandDTO {
    /**
     * Custom unique id for the evidence type. If null, a random id will be generated.
     */
    val id: EvidenceTypeListId?

    /**
     * Custom unique identifier for the evidence type. If null, a random id will be generated.
     */
    val identifier: EvidenceTypeListIdentifier?

    val name: String

    val description: String

    val specifiesEvidenceType: List<EvidenceTypeListIdentifier>?

}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListCreateCommand(
    override val id: EvidenceTypeListId? = null,
    override val identifier: EvidenceTypeListIdentifier? = null,
    override val specifiesEvidenceType: List<EvidenceTypeListIdentifier>? = null,
    override val name: String,
    override val description: String
): EvidenceTypeListCreateCommandDTO

/**
 * @d2 event
 * @parent [EvidenceTypeListCreateFunction]
 */
@JsExport
@JsName("EvidenceTypeListCreatedEventDTO")
interface EvidenceTypeListCreatedEventDTO {
    /**
     * Id of the evidence type as specified in the command, or random if not.
     */
    val id: EvidenceTypeListId
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListCreatedEvent(
    override val id: EvidenceTypeListId,
): EvidenceTypeListCreatedEventDTO

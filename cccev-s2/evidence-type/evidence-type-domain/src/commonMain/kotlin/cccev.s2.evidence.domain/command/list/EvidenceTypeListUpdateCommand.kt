package cccev.s2.evidence.domain.command.list

import cccev.s2.evidence.domain.D2EvidenceTypePage
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListCommand
import cccev.s2.evidence.domain.EvidenceTypeListEvent
import cccev.s2.evidence.domain.EvidenceTypeListId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Update an evidence type list.
 * @d2 function
 * @parent [D2EvidenceTypePage]
 * @order 110
 */
interface EvidenceTypeListUpdateFunction

@JsExport
@JsName("EvidenceTypeListUpdateCommandDTO")
interface EvidenceTypeListUpdateCommandDTO: EvidenceTypeListCommand {
    /**
     * Identifier of the evidence type list to update.
     */
    override val id: EvidenceTypeListId

    /**
     * Name of the evidence type list.
     * @example [cccev.s2.evidence.domain.model.EvidenceTypeList.name]
     */
    val name: String

    /**
     * Description of the evidence type list.
     * @example [cccev.s2.evidence.domain.model.EvidenceTypeList.description]
     */
    val description: String

    /**
     *
     * @example [cccev.s2.evidence.domain.model.EvidenceTypeList.specifiesEvidenceType]
     */
    val specifiesEvidenceType: List<EvidenceTypeId>
}

/**
 * @d2 command
 * @parent [EvidenceTypeListUpdateFunction]
 */
@Serializable
data class EvidenceTypeListUpdateCommand(
    override val id: EvidenceTypeListId,
    override val name: String,
    override val description: String,
    override val specifiesEvidenceType: List<EvidenceTypeId>
): EvidenceTypeListUpdateCommandDTO

@JsExport
@JsName("EvidenceTypeListUpdatedEventDTO")
interface EvidenceTypeListUpdatedEventDTO: EvidenceTypeListEvent {
    /**
     * Identifier of the updated evidence type list.
     */
    val id: EvidenceTypeListId
}

/**
 * @d2 event
 * @parent [EvidenceTypeListUpdateFunction]
 */
@Serializable
data class EvidenceTypeListUpdatedEvent(
    override val id: EvidenceTypeListId
): EvidenceTypeListUpdatedEventDTO {
    override fun s2Id() = id
}

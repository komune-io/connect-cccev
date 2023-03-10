package cccev.s2.evidence.domain.command.list

import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListCommand
import cccev.s2.evidence.domain.EvidenceTypeListEvent
import cccev.s2.evidence.domain.EvidenceTypeListId
import kotlinx.serialization.Serializable

/**
 * Add a list of evidence types to an evidence type list.
 * @d2 function
 * @parent [D2EvidenceTypeListPage]
 * @order 30
 */
interface EvidenceTypeListAddEvidenceTypesFunction

/**
 * @d2 command
 * @parent [EvidenceTypeListAddEvidenceTypesFunction]
 */
data class EvidenceTypeListAddEvidenceTypesCommand(
    /**
     * Id of the evidence type list to add evidence types to.
     */
    override val id: EvidenceTypeListId,

    /**
     * Ids of the evidence types to add.
     */
    val evidenceTypeIds: List<EvidenceTypeId>
): EvidenceTypeListCommand

/**
 * @d2 event
 * @parent [EvidenceTypeListAddEvidenceTypesFunction]
 */
@Serializable
data class EvidenceTypeListAddedEvidenceTypesEvent(
    /** @ref [EvidenceTypeListAddEvidenceTypesCommand.id] */
    override val id: EvidenceTypeListId,

    /** @ref [EvidenceTypeListAddEvidenceTypesCommand.evidenceTypeIds] */
    val evidenceTypeIds: List<EvidenceTypeId>
): EvidenceTypeListEvent

package cccev.s2.evidence.domain

import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemoveEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdatedEvent
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreateCommand
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreatedEvent

interface EvidenceTypeAggregate {
    suspend fun create(command: EvidenceTypeCreateCommand): EvidenceTypeCreatedEvent

    suspend fun createList(command: EvidenceTypeListCreateCommand): EvidenceTypeListCreatedEvent
    suspend fun updateList(command: EvidenceTypeListUpdateCommand): EvidenceTypeListUpdatedEvent
    suspend fun addEvidenceTypes(command: EvidenceTypeListAddEvidenceTypesCommand): EvidenceTypeListAddedEvidenceTypesEvent
    suspend fun removeEvidenceTypes(command: EvidenceTypeListRemoveEvidenceTypesCommand): EvidenceTypeListRemovedEvidenceTypesEvent
}

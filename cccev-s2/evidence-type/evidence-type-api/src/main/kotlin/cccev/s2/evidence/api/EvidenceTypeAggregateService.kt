package cccev.s2.evidence.api

import cccev.s2.evidence.api.entity.list.EvidenceTypeListAutomateExecutor
import cccev.s2.evidence.api.entity.type.EvidenceTypeAutomateExecutor
import cccev.s2.evidence.domain.EvidenceTypeAggregate
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListState
import cccev.s2.evidence.domain.EvidenceTypeState
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdatedEvent
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreateCommand
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreatedEvent
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EvidenceTypeAggregateService(
    private val typeAutomate: EvidenceTypeAutomateExecutor,
    private val listAutomate: EvidenceTypeListAutomateExecutor,
    private val evidenceTypeFinderService: EvidenceTypeFinderService
): EvidenceTypeAggregate {
    override suspend fun create(command: EvidenceTypeCreateCommand) = typeAutomate.init(command) {
        EvidenceTypeCreatedEvent(
            id = UUID.randomUUID().toString(),
            name = command.name,
            description = command.description,
            validityPeriodConstraint = command.validityPeriodConstraint,
            status = EvidenceTypeState.EXISTS,
        )
    }

    override suspend fun createList(command: EvidenceTypeListCreateCommand) = listAutomate.init(command) {
        checkEvidenceTypesExist(command.specifiesEvidenceType)

        EvidenceTypeListCreatedEvent(
            id = UUID.randomUUID().toString(),
            name = command.name,
            description = command.description,
            specifiesEvidenceType = command.specifiesEvidenceType,
            status = EvidenceTypeListState.EXISTS
        )
    }

    override suspend fun updateList(command: EvidenceTypeListUpdateCommand) = listAutomate.transition(command) {
        checkEvidenceTypesExist(command.specifiesEvidenceType)

//        it.name = command.name
//        it.description = command.description
//        it.specifiesEvidenceType = command.specifiesEvidenceType

        EvidenceTypeListUpdatedEvent(command.id)
    }

    private suspend fun checkEvidenceTypesExist(ids: Collection<EvidenceTypeId>) {
        ids.forEach { evidenceTypeFinderService.get(it) }
    }
}

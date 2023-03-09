package cccev.f2.concept.api.service

import cccev.s2.concept.api.InformationConceptAggregateService
import cccev.s2.concept.api.InformationConceptFinderService
import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import cccev.s2.unit.api.DataUnitFinderService
import cccev.s2.unit.domain.DataUnitId
import org.springframework.stereotype.Service

@Service
class InformationConceptF2AggregateService(
    private val dataUnitFinderService: DataUnitFinderService,
    private val informationConceptAggregateService: InformationConceptAggregateService,
    private val informationConceptFinderService: InformationConceptFinderService
) {
    suspend fun create(command: InformationConceptCreateCommand): InformationConceptCreatedEvent {
        checkConceptsExist(command.dependsOn)
        checkUnitExists(command.hasUnit)
        return informationConceptAggregateService.create(command)
    }

    private suspend fun checkConceptsExist(ids: Collection<DataUnitId>) {
        ids.forEach { informationConceptFinderService.get(it) }
    }

    private suspend fun checkUnitExists(id: DataUnitId) {
        dataUnitFinderService.get(id)
    }
}

package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.evidencetype.EvidenceTypeRepository
import cccev.s2.evidence.type.domain.EvidenceTypeListEvent
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import s2.sourcing.dsl.view.View

@Service
class EvidenceTypeListEvolver(
	private val evidenceTypeRepository: EvidenceTypeRepository,
): View<EvidenceTypeListEvent, EvidenceTypeListEntity> {

	override suspend fun evolve(
        event: EvidenceTypeListEvent, model: EvidenceTypeListEntity?
	): EvidenceTypeListEntity? = when (event) {
		is EvidenceTypeListCreatedEvent -> create(event)
		is EvidenceTypeListAddedEvidenceTypesEvent -> model?.addEvidenceType(event)
		is EvidenceTypeListRemovedEvidenceTypesEvent -> model?.removeEvidenceType(event)
		else -> TODO()
	}

	private suspend fun create(event: EvidenceTypeListCreatedEvent): EvidenceTypeListEntity {
		val evidenceTypes = evidenceTypeRepository.findByIdIn(event.specifiesEvidenceType).collectList().awaitSingle()

		return EvidenceTypeListEntity(
			id = event.id,
			identifier = event.identifier,
			name = event.name,
			description = event.description,
			specifiesEvidenceType = evidenceTypes,
			status = event.status
		)
	}

	private suspend fun EvidenceTypeListEntity.addEvidenceType(event: EvidenceTypeListAddedEvidenceTypesEvent) = copy(
		specifiesEvidenceType = specifiesEvidenceType + evidenceTypeRepository.findByIdIn(event.evidenceTypeIds).collectList().awaitSingle(),
	)

	private suspend fun EvidenceTypeListEntity.removeEvidenceType(event: EvidenceTypeListRemovedEvidenceTypesEvent) = copy(
		specifiesEvidenceType = specifiesEvidenceType.filter { it.id !in event.evidenceTypeIds }
	)
}

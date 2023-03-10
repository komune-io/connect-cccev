package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.evidencetype.EvidenceTypeRepository
import cccev.s2.evidence.domain.EvidenceTypeListEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
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

		return EvidenceTypeListEntity().apply {
			id = event.id
			name = event.name
			description = event.description
			specifiesEvidenceType = evidenceTypes
			status = event.status
		}
	}

	private suspend fun EvidenceTypeListEntity.addEvidenceType(event: EvidenceTypeListAddedEvidenceTypesEvent) = apply {
		specifiesEvidenceType += evidenceTypeRepository.findByIdIn(event.evidenceTypeIds).collectList().awaitSingle()
	}

	private suspend fun EvidenceTypeListEntity.removeEvidenceType(event: EvidenceTypeListRemovedEvidenceTypesEvent) = apply {
		specifiesEvidenceType.removeIf { it.id in event.evidenceTypeIds }
	}
}

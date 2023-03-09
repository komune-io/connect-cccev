package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.evidencetype.EvidenceTypeRepository
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEntity
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.domain.EvidenceTypeListEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import s2.sourcing.dsl.view.View

@Service
class EvidenceTypeListEvolver(
	private val evidenceTypeListRepository: EvidenceTypeListRepository,
	private val evidenceTypeRepository: EvidenceTypeRepository,
): View<EvidenceTypeListEvent, EvidenceTypeListEntity> {

	override suspend fun evolve(
		event: EvidenceTypeListEvent, model: EvidenceTypeListEntity?
	): EvidenceTypeListEntity? = when (event) {
		is EvidenceTypeListCreatedEvent -> created(event)
		else -> TODO()
	}

	private suspend fun created(event: EvidenceTypeListCreatedEvent): EvidenceTypeListEntity {
		val evidenceTypes = evidenceTypeRepository.findByIdIn(event.specifiesEvidenceType).collectList().awaitSingle()

		return EvidenceTypeListEntity().apply {
			id = event.id
			name = event.name
			description = event.description
			specifiesEvidenceType = evidenceTypes
			status = event.status
		}
	}
}

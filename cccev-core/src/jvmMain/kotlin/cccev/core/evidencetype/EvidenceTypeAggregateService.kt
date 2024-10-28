package cccev.core.evidencetype

import cccev.core.concept.entity.InformationConceptRepository
import cccev.core.evidencetype.entity.EvidenceType
import cccev.f2.evidencetype.command.EvidenceTypeCreateCommand
import cccev.f2.evidencetype.command.EvidenceTypeCreatedEvent
import cccev.infra.neo4j.checkNotExists
import cccev.infra.neo4j.session
import f2.spring.exception.NotFoundException
import java.util.UUID
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class EvidenceTypeAggregateService(
    private val informationConceptRepository: InformationConceptRepository,
    private val sessionFactory: SessionFactory
) {
    suspend fun create(command: EvidenceTypeCreateCommand): EvidenceTypeCreatedEvent = sessionFactory.session { session ->
        command.id?.let { id ->
            session.checkNotExists<EvidenceType>(id, "EvidenceType")
        }

        val shallowConcepts = command.conceptIdentifiers.map { identifier ->
            informationConceptRepository.findShallowByIdentifier(identifier)
                ?: throw NotFoundException("InformationConcept", identifier)
        }

        val evidenceType = EvidenceType().apply {
            id = command.id ?: UUID.randomUUID().toString()
            name = command.name
            concepts = shallowConcepts.toMutableList()
        }
        session.save(evidenceType)

        EvidenceTypeCreatedEvent(evidenceType.id)
    }
}

package cccev.core.concept

import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.concept.entity.InformationConceptRepository
import cccev.dsl.model.InformationConceptId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class InformationConceptFinderService(
    private val informationConceptRepository: InformationConceptRepository,
) {
    suspend fun getOrNull(id: InformationConceptId): InformationConceptEntity? {
        return informationConceptRepository.findById(id)
    }

    suspend fun get(id: InformationConceptId): InformationConceptEntity {
        return getOrNull(id)
            ?: throw NotFoundException("InformationConcept", id)
    }

    suspend fun getByIdentifierOrNull(identifier: InformationConceptId): InformationConceptEntity? {
        return informationConceptRepository.findByIdentifier(identifier)
    }

    suspend fun getByIdentifier(identifier: InformationConceptId): InformationConceptEntity {
        return getByIdentifierOrNull(identifier)
            ?: throw NotFoundException("InformationConcept with identifier", identifier)
    }
}

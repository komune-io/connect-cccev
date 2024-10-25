package cccev.core.concept

import cccev.core.concept.entity.InformationConcept
import cccev.core.concept.entity.InformationConceptRepository
import cccev.core.concept.model.InformationConceptId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class InformationConceptFinderService(
    private val informationConceptRepository: InformationConceptRepository,
) {
    suspend fun getOrNull(id: InformationConceptId): InformationConcept? {
        return informationConceptRepository.findById(id)
    }

    suspend fun get(id: InformationConceptId): InformationConcept {
        return getOrNull(id)
            ?: throw NotFoundException("InformationConcept", id)
    }

    suspend fun getByIdentifierOrNull(identifier: InformationConceptId): InformationConcept? {
        return informationConceptRepository.findByIdentifier(identifier)
    }

    suspend fun getByIdentifier(identifier: InformationConceptId): InformationConcept {
        return getByIdentifierOrNull(identifier)
            ?: throw NotFoundException("InformationConcept with identifier", identifier)
    }
}

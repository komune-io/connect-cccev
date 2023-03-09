package cccev.s2.concept.api

import cccev.projection.api.entity.concept.InformationConceptRepository
import cccev.s2.concept.api.entity.toInformationConcept
import cccev.s2.concept.domain.InformationConceptFinder
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.concept.domain.model.InformationConcept
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class InformationConceptFinderService(
    private val informationConceptRepository: InformationConceptRepository
): InformationConceptFinder {
    override suspend fun getOrNull(id: InformationConceptId): InformationConcept? {
        return informationConceptRepository.findById(id)
            .awaitSingleOrNull()
            ?.toInformationConcept()
    }

    override suspend fun get(id: InformationConceptId): InformationConcept {
        return getOrNull(id) ?: throw NotFoundException("InformationConcept", id)
    }
}

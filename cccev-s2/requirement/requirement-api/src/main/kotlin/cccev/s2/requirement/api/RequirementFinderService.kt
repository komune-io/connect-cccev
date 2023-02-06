package cccev.s2.requirement.api

import cccev.s2.requirement.api.entity.RequirementRepository
import cccev.s2.requirement.api.entity.toRequirement
import cccev.s2.requirement.domain.RequirementFinder
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.Requirement
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class RequirementFinderService(
    private val requirementRepository: RequirementRepository
): RequirementFinder {
    override suspend fun getOrNull(id: RequirementId): Requirement? {
        return requirementRepository.findById(id)
            .awaitSingleOrNull()
            ?.toRequirement()
    }

    override suspend fun get(id: RequirementId): Requirement {
        return getOrNull(id) ?: throw NotFoundException("Requirement", id)
    }
}

package cccev.core.requirement

import cccev.core.requirement.entity.Requirement
import cccev.core.requirement.entity.RequirementRepository
import cccev.core.requirement.model.RequirementId
import cccev.core.requirement.model.RequirementIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class RequirementFinderService(
    private val requirementRepository: RequirementRepository
) {
    suspend fun getOrNull(id: RequirementId): Requirement? {
        return requirementRepository.findById(id)
    }

    suspend fun get(id: RequirementId): Requirement {
        return getOrNull(id)
            ?: throw NotFoundException("Requirement", id)
    }

    suspend fun getOrNullByIdentifier(identifier: RequirementIdentifier): Requirement? {
        return requirementRepository.findByIdentifier(identifier)
    }
}

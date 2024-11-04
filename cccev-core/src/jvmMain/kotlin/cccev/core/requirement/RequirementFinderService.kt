package cccev.core.requirement

import cccev.core.requirement.entity.RequirementEntity
import cccev.core.requirement.entity.RequirementRepository
import cccev.dsl.model.RequirementId
import cccev.dsl.model.RequirementIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class RequirementFinderService(
    private val requirementRepository: RequirementRepository
) {
    suspend fun getOrNull(id: RequirementId): RequirementEntity? {
        return requirementRepository.findById(id)
    }

    suspend fun get(id: RequirementId): RequirementEntity {
        return getOrNull(id)
            ?: throw NotFoundException("Requirement", id)
    }

    suspend fun getOrNullByIdentifier(identifier: RequirementIdentifier): RequirementEntity? {
        return requirementRepository.findByIdentifier(identifier)
    }
}

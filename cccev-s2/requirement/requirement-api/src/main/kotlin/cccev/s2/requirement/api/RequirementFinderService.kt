package cccev.s2.requirement.api

import cccev.dsl.model.EvidenceTypeId
import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.requirement.api.entity.toRequirement
import cccev.s2.requirement.domain.RequirementFinder
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.Requirement
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
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

    override suspend fun listByIdWithChildrenOfType(ids: List<RequirementId>, type: String): List<Requirement> {
        return requirementRepository.findByIdentifierWithChildrenOfType(ids, type)
            .map(RequirementEntity::toRequirement)
            .collectList().awaitSingle()
    }

    override suspend fun list(
        isRequirementOf: RequirementId?,
        concept: InformationConceptId?,
        evidenceType: EvidenceTypeId?
    ): Flow<Requirement> {
        val requirements = isRequirementOf?.let {
            requirementRepository.findByIdentifier(isRequirementOf).awaitSingle().hasRequirement.asFlow()
//            requirementRepository.findAllByIsRequirementOf(isRequirementOf).asFlow()
        } ?: requirementRepository.findAll().asFlow()

        return requirements.map { it.toRequirement() }
    }
}

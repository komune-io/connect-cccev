package cccev.s2.requirement.api.entity

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.Requirement
import org.springframework.stereotype.Component
import s2.sourcing.dsl.snap.SnapRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull

@Component
class RequirementSnapRepository(
    private val requirementRepository: RequirementRepository,
): SnapRepository<Requirement, RequirementId>  {

    override suspend fun get(id: String): Requirement? {
        return requirementRepository.findById(id).awaitSingleOrNull()?.toRequirement()
    }

    override suspend fun save(entity: Requirement): Requirement {
        return requirementRepository.save(entity.toEntity()).awaitSingle().toRequirement()
    }

    override suspend fun remove(id: String): Boolean {
        requirementRepository.deleteById(id).awaitSingle()
        return true
    }
}


fun Requirement.toEntity(): RequirementEntity {
    val requirement = this
    return RequirementEntity().apply {
        id = requirement.id
        identifier = requirement.identifier
        kind = requirement.kind
        description = requirement.description
        name = requirement.name
        kind = requirement.kind
        status = requirement.state
        hasRequirement = emptyList()
        hasConcept = emptyList()
        hasEvidenceTypeList = emptyList()
        isRequirementOf = emptyList()
        hasQualifiedRelation = emptyList()
    }
}

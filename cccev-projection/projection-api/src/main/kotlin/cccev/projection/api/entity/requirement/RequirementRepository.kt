package cccev.projection.api.entity.requirement

import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.RequirementIdentifier
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface RequirementRepository: ReactiveNeo4jRepository<RequirementEntity, RequirementId> {
    fun findByIdentifier(id: RequirementIdentifier): Mono<RequirementEntity>
//    fun findAllByIsRequirementOf(id: RequirementId): Mono<RequirementEntity>
}

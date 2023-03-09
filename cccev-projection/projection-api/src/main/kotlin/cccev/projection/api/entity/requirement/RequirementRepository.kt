package cccev.projection.api.entity.requirement

import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.model.RequirementIdentifier
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import s2.sourcing.dsl.snap.SnapRepository

@Repository
interface RequirementRepository: ReactiveNeo4jRepository<RequirementEntity, RequirementId> {
    fun findByIdentifier(id: RequirementIdentifier): Mono<RequirementEntity>
    fun findByIdentifierIn(ids: Collection<RequirementIdentifier>): Flux<RequirementEntity>
//    fun findAllByIsRequirementOf(id: RequirementId): Mono<RequirementEntity>
}

@Service
class RequirementSnapRepository(
    private val repository: RequirementRepository
): SnapRepository<RequirementEntity, RequirementId> {
    override suspend fun get(id: RequirementId): RequirementEntity? {
        return repository.findById(id).awaitSingleOrNull()
    }

    override suspend fun remove(id: RequirementId): Boolean {
        repository.deleteById(id).awaitSingle()
        return true
    }

    override suspend fun save(entity: RequirementEntity): RequirementEntity {
        return repository.save(entity).awaitSingle()
    }
}

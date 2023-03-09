package cccev.projection.api.entity.concept

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.concept.domain.InformationConceptId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import s2.sourcing.dsl.snap.SnapRepository

interface InformationConceptRepository: ReactiveNeo4jRepository<InformationConceptEntity, InformationConceptId> {
    fun findByIdIn(ids: Collection<InformationConceptId>): Flux<InformationConceptEntity>
}

@Service
class InformationConceptSnapRepository(
    private val repository: InformationConceptRepository
): SnapRepository<InformationConceptEntity, InformationConceptId> {
    override suspend fun get(id: InformationConceptId): InformationConceptEntity? {
        return repository.findById(id).awaitSingleOrNull()
    }

    override suspend fun remove(id: InformationConceptId): Boolean {
        repository.deleteById(id).awaitSingle()
        return true
    }

    override suspend fun save(entity: InformationConceptEntity): InformationConceptEntity {
        return repository.save(entity).awaitSingle()
    }
}

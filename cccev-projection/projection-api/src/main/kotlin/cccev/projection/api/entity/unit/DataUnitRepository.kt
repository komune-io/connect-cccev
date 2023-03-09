package cccev.projection.api.entity.unit

import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.projection.api.entity.concept.InformationConceptRepository
import cccev.s2.unit.domain.DataUnitId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Service
import s2.sourcing.dsl.snap.SnapRepository

interface DataUnitRepository: ReactiveNeo4jRepository<DataUnitEntity, DataUnitId>

@Service
class DataUnitSnapRepository(
    private val repository: DataUnitRepository
): SnapRepository<DataUnitEntity, DataUnitId> {
    override suspend fun get(id: DataUnitId): DataUnitEntity? {
        return repository.findById(id).awaitSingleOrNull()
    }

    override suspend fun remove(id: DataUnitId): Boolean {
        repository.deleteById(id).awaitSingle()
        return true
    }

    override suspend fun save(entity: DataUnitEntity): DataUnitEntity {
        return repository.save(entity).awaitSingle()
    }
}

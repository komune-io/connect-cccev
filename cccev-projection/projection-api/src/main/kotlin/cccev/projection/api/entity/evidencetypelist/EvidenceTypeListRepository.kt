package cccev.projection.api.entity.evidencetypelist

import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.projection.api.entity.evidencetype.EvidenceTypeRepository
import cccev.s2.evidence.domain.EvidenceTypeListId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import s2.sourcing.dsl.snap.SnapRepository

interface EvidenceTypeListRepository: ReactiveNeo4jRepository<EvidenceTypeListEntity, EvidenceTypeListId> {
    fun findByIdIn(ids: Collection<String>): Flux<EvidenceTypeListEntity>
}

@Service
class EvidenceTypeListSnapRepository(
    private val repository: EvidenceTypeListRepository
): SnapRepository<EvidenceTypeListEntity, EvidenceTypeListId> {
    override suspend fun get(id: EvidenceTypeListId): EvidenceTypeListEntity? {
        return repository.findById(id).awaitSingleOrNull()
    }

    override suspend fun remove(id: EvidenceTypeListId): Boolean {
        repository.deleteById(id).awaitSingle()
        return true
    }

    override suspend fun save(entity: EvidenceTypeListEntity): EvidenceTypeListEntity {
        return repository.save(entity).awaitSingle()
    }
}

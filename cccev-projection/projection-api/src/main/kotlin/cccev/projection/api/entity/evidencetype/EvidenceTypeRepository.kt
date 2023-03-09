package cccev.projection.api.entity.evidencetype

import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.s2.evidence.domain.EvidenceTypeId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import s2.sourcing.dsl.snap.SnapRepository

interface EvidenceTypeRepository: ReactiveNeo4jRepository<EvidenceTypeEntity, EvidenceTypeId> {
    fun findByIdIn(ids: Collection<EvidenceTypeId>): Flux<EvidenceTypeEntity>
}

@Service
class EvidenceTypeSnapRepository(
    private val repository: EvidenceTypeRepository
): SnapRepository<EvidenceTypeEntity, EvidenceTypeId> {
    override suspend fun get(id: EvidenceTypeId): EvidenceTypeEntity? {
        return repository.findById(id).awaitSingleOrNull()
    }

    override suspend fun remove(id: EvidenceTypeId): Boolean {
        repository.deleteById(id).awaitSingle()
        return true
    }

    override suspend fun save(entity: EvidenceTypeEntity): EvidenceTypeEntity {
        return repository.save(entity).awaitSingle()
    }
}

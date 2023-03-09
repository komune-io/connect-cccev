package cccev.s2.unit.api

import cccev.projection.api.entity.unit.DataUnitRepository
import cccev.s2.unit.api.entity.toDataUnit
import cccev.s2.unit.domain.DataUnitFinder
import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.model.DataUnit
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class DataUnitFinderService(
    private val dataUnitRepository: DataUnitRepository
): DataUnitFinder {
    override suspend fun getOrNull(id: DataUnitId): DataUnit? {
        return dataUnitRepository.findById(id)
            .awaitSingleOrNull()
            ?.toDataUnit()
    }

    override suspend fun get(id: DataUnitId): DataUnit {
        return getOrNull(id) ?: throw NotFoundException("DataUnit", id)
    }
}

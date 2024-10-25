package cccev.core.unit

import cccev.core.unit.entity.DataUnit
import cccev.core.unit.entity.DataUnitRepository
import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class DataUnitFinderService(
    private val dataUnitRepository: DataUnitRepository
) {
    suspend fun getOrNull(id: DataUnitId): DataUnit? {
        return dataUnitRepository.findById(id)
    }

    suspend fun get(id: DataUnitId): DataUnit {
        return getOrNull(id)
            ?: throw NotFoundException("DataUnit", id)
    }

    suspend fun getByIdentifierOrNull(id: DataUnitIdentifier): DataUnit? {
        return dataUnitRepository.findByIdentifier(id)
    }
}

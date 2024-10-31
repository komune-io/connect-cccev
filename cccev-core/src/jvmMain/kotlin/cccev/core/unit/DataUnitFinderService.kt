package cccev.core.unit

import cccev.core.unit.entity.DataUnitEntity
import cccev.core.unit.entity.DataUnitRepository
import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class DataUnitFinderService(
    private val dataUnitRepository: DataUnitRepository
) {
    suspend fun getOrNull(id: DataUnitId): DataUnitEntity? {
        return dataUnitRepository.findById(id)
    }

    suspend fun get(id: DataUnitId): DataUnitEntity {
        return getOrNull(id)
            ?: throw NotFoundException("DataUnit", id)
    }

    suspend fun getByIdentifierOrNull(id: DataUnitIdentifier): DataUnitEntity? {
        return dataUnitRepository.findByIdentifier(id)
    }
}

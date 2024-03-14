package cccev.f2.unit.api.service

import cccev.core.unit.DataUnitFinderService
import cccev.core.unit.entity.DataUnit
import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import org.springframework.stereotype.Service

@Service
class DataUnitF2FinderService(
    private val dataUnitFinderService: DataUnitFinderService
) {
    suspend fun getOrNull(id: DataUnitId): DataUnit? {
        return dataUnitFinderService.getOrNull(id)
    }

    suspend fun get(id: DataUnitId): DataUnit {
        return dataUnitFinderService.get(id)
    }

    suspend fun getByIdentifierOrNull(id: DataUnitIdentifier): DataUnit? {
        return dataUnitFinderService.getByIdentifierOrNull(id)
    }
}

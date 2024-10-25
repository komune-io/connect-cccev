package cccev.f2.unit

import cccev.core.unit.command.DataUnitCreateFunction
import cccev.core.unit.command.DataUnitUpdateFunction
import cccev.f2.unit.query.DataUnitGetByIdentifierFunction
import cccev.f2.unit.query.DataUnitGetFunction

/**
 * @d2 api
 * @parent [cccev.core.unit.D2DataUnitPage]
 */
interface DataUnitApi: DataUnitCommandApi, DataUnitQueryApi

interface DataUnitCommandApi {
    fun dataUnitCreate(): DataUnitCreateFunction
    fun dataUnitUpdate(): DataUnitUpdateFunction
}

interface DataUnitQueryApi {
    fun dataUnitGet(): DataUnitGetFunction
    fun dataUnitGetByIdentifier(): DataUnitGetByIdentifierFunction
}

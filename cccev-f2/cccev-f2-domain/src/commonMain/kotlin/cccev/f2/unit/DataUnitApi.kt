package cccev.f2.unit

import cccev.f2.unit.command.DataUnitCreateFunction
import cccev.f2.unit.command.DataUnitUpdateFunction
import cccev.f2.unit.query.DataUnitGetByIdentifierFunction
import cccev.f2.unit.query.DataUnitGetFunction

/**
 * @d2 api
 * @order 50
 * @parent [cccev.dsl.model.d2.D2DataUnitPage]
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

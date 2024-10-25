package cccev.f2.unit.query

import cccev.core.unit.model.DataUnitId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.unit.model.DataUnitFlat
import cccev.f2.unit.model.DataUnitFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a data unit by its id.
 * @d2 function
 * @parent [cccev.core.unit.D2DataUnitPage]
 */
typealias DataUnitGetFunction = F2Function<DataUnitGetQuery, DataUnitGetResult>

/**
 * @d2 query
 * @parent [DataUnitGetFunction]
 */
@JsExport
@JsName("DataUnitGetQueryDTO")
interface DataUnitGetQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val id: DataUnitId
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitGetQuery(
    override val id: DataUnitId
): DataUnitGetQueryDTO

/**
 * @d2 result
 * @parent [DataUnitGetFunction]
 */
@JsExport
@JsName("DataUnitGetResultDTO")
interface DataUnitGetResultDTO {
    val item: DataUnitFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitGetResult(
    override val item: DataUnitFlat?,
    override val graph: CccevFlatGraph
): DataUnitGetResultDTO

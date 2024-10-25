package cccev.f2.unit.query

import cccev.core.unit.model.DataUnitIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.unit.model.DataUnitFlat
import cccev.f2.unit.model.DataUnitFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a data unit by its identifier.
 * @d2 function
 * @parent [cccev.core.unit.D2DataUnitPage]
 */
typealias DataUnitGetByIdentifierFunction = F2Function<DataUnitGetByIdentifierQuery, DataUnitGetByIdentifierResult>

/**
 * @d2 query
 * @parent [DataUnitGetByIdentifierFunction]
 */
@JsExport
@JsName("DataUnitGetByIdentifierQueryDTO")
interface DataUnitGetByIdentifierQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val identifier: DataUnitIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitGetByIdentifierQuery(
    override val identifier: DataUnitIdentifier
): DataUnitGetByIdentifierQueryDTO

/**
 * @d2 result
 * @parent [DataUnitGetByIdentifierFunction]
 */
@JsExport
@JsName("DataUnitGetByIdentifierResultDTO")
interface DataUnitGetByIdentifierResultDTO {
    val item: DataUnitFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitGetByIdentifierResult(
    override val item: DataUnitFlat?,
    override val graph: CccevFlatGraph
): DataUnitGetByIdentifierResultDTO

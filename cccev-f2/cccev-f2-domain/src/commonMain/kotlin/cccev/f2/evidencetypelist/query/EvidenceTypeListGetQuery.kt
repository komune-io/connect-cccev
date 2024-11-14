package cccev.f2.evidencetypelist.query

import cccev.dsl.model.EvidenceTypeId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.evidencetype.D2EvidenceTypePage
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlatDTO
import cccev.f2.evidencetypelist.model.EvidenceTypeListFlat
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get an evidence type by its id.
 * @d2 function
 * @parent [cccev.f2.evidencetype.D2EvidenceTypePage]
 */
typealias EvidenceTypeListGetFunction = F2Function<EvidenceTypeListGetQuery, EvidenceTypeListGetResult>

/**
 * @d2 query
 * @parent [EvidenceTypeListGetFunction]
 */
@JsExport
@JsName("EvidenceTypeListGetQueryDTO")
interface EvidenceTypeListGetQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val id: EvidenceTypeId
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListGetQuery(
    override val id: EvidenceTypeId
): EvidenceTypeListGetQueryDTO

/**
 * @d2 result
 * @parent [EvidenceTypeListGetFunction]
 */
@JsExport
interface EvidenceTypeListGetResultDTO {
    val item: EvidenceTypeListFlat?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListGetResult(
    override val item: EvidenceTypeListFlat?,
    override val graph: CccevFlatGraph
): EvidenceTypeListGetResultDTO

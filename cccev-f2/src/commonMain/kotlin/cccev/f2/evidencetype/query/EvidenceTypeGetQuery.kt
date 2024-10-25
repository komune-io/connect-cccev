package cccev.f2.evidencetype.query

import cccev.core.evidencetype.D2EvidenceTypePage
import cccev.core.evidencetype.model.EvidenceTypeId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get an evidence type by its id.
 * @d2 function
 * @parent [D2EvidenceTypePage]
 */
typealias EvidenceTypeGetFunction = F2Function<EvidenceTypeGetQuery, EvidenceTypeGetResult>

/**
 * @d2 query
 * @parent [EvidenceTypeGetFunction]
 */
@JsExport
@JsName("EvidenceTypeGetQueryDTO")
interface EvidenceTypeGetQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val id: EvidenceTypeId
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeGetQuery(
    override val id: EvidenceTypeId
): EvidenceTypeGetQueryDTO

/**
 * @d2 result
 * @parent [EvidenceTypeGetFunction]
 */
@JsExport
interface EvidenceTypeGetResultDTO {
    val item: EvidenceTypeFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeGetResult(
    override val item: EvidenceTypeFlat?,
    override val graph: CccevFlatGraph
): EvidenceTypeGetResultDTO

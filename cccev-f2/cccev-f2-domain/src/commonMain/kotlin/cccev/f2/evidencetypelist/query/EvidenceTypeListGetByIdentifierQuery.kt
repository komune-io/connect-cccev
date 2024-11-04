package cccev.f2.evidencetypelist.query

import cccev.dsl.model.EvidenceTypeIdentifier
import cccev.dsl.model.EvidenceTypeListIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.evidencetype.D2EvidenceTypePage
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlatDTO
import cccev.f2.evidencetypelist.model.EvidenceTypeListFlat
import cccev.f2.evidencetypelist.model.EvidenceTypeListFlatDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get an evidence type by its identifier.
 * @d2 function
 * @parent [D2EvidenceTypePage]
 */
typealias EvidenceTypeListGetByIdentifierFunction = F2Function<EvidenceTypeListGetByIdentifierQuery, EvidenceTypeListGetByIdentifierResult>

/**
 * @d2 query
 * @parent [EvidenceTypeListGetFunction]
 */
@JsExport
@JsName("EvidenceTypeListGetByIdentifierQueryDTO")
interface EvidenceTypeListGetByIdentifierQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val identifier: EvidenceTypeListIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListGetByIdentifierQuery(
    override val identifier: EvidenceTypeListIdentifier
): EvidenceTypeListGetByIdentifierQueryDTO

/**
 * @d2 result
 * @parent [EvidenceTypeListGetFunction]
 */
@JsExport
interface EvidenceTypeListGetByIdentifierResultDTO {
    val item: EvidenceTypeListFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListGetByIdentifierResult(
    override val item: EvidenceTypeListFlat?,
    override val graph: CccevFlatGraph
): EvidenceTypeListGetByIdentifierResultDTO

package cccev.f2.evidencetype.query

import cccev.dsl.model.EvidenceTypeIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlatDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get an evidence type by its identifier.
 * @d2 function
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
typealias EvidenceTypeGetByIdentifierFunction = F2Function<EvidenceTypeGetByIdentifierQuery, EvidenceTypeGetByIdentifierResult>

/**
 * @d2 query
 * @parent [EvidenceTypeGetFunction]
 */
@JsExport
@JsName("EvidenceTypeGetByIdentifierQueryDTO")
interface EvidenceTypeGetByIdentifierQueryDTO {
    /**
     * Identifier of the data unit to get.
     */
    val identifier: EvidenceTypeIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeGetByIdentifierQuery(
    override val identifier: EvidenceTypeIdentifier
): EvidenceTypeGetByIdentifierQueryDTO

/**
 * @d2 result
 * @parent [EvidenceTypeGetFunction]
 */
@JsExport
interface EvidenceTypeGetByIdentifierResultDTO {
    val item: EvidenceTypeFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeGetByIdentifierResult(
    override val item: EvidenceTypeFlat?,
    override val graph: CccevFlatGraph
): EvidenceTypeGetByIdentifierResultDTO

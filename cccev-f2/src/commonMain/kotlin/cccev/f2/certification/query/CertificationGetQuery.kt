package cccev.f2.certification.query

import cccev.core.certification.model.CertificationId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.certification.model.CertificationFlat
import cccev.f2.certification.model.CertificationFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a certification by id, or null if it does not exist. The certification graph will be flattened.
 * @d2 function
 * @parent [cccev.core.certification.D2CertificationPage]
 */
typealias CertificationGetFunction = F2Function<CertificationGetQuery, CertificationGetResult>

/**
 * @d2 query
 * @parent [CertificationGetFunction]
 */
@JsExport
@JsName("CertificationGetQueryDTO")
interface CertificationGetQueryDTO {
    /**
     * Id of the certification to get
     */
    val id: CertificationId
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationGetQuery(
    override val id: CertificationId
): CertificationGetQueryDTO

/**
 * @d2 result
 */
@JsExport
@JsName("CertificationGetResultDTO")
interface CertificationGetResultDTO {
    /**
     * The certification, or null if it does not exist.
     */
    val certification: CertificationFlatDTO?

    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationGetResult(
    override val certification: CertificationFlat?,
    override val graph: CccevFlatGraph
): CertificationGetResultDTO

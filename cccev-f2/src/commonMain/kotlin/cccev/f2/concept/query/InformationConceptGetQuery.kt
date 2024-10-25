package cccev.f2.concept.query

import cccev.core.concept.model.InformationConceptId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.concept.model.InformationConceptFlat
import cccev.f2.concept.model.InformationConceptFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Get an information concept by its id.
 * @d2 function
 * @parent [cccev.core.concept.D2InformationConceptPage]
 */
typealias InformationConceptGetFunction = F2Function<InformationConceptGetQuery, InformationConceptGetResult>

/**
 * @d2 query
 * @parent [InformationConceptGetFunction]
 */
@JsExport
interface InformationConceptGetQueryDTO {
    /**
     * Identifier of the information concept to get.
     */
    val id: InformationConceptId
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptGetQuery(
    override val id: InformationConceptId
): InformationConceptGetQueryDTO

/**
 * @d2 result
 * @parent [InformationConceptGetFunction]
 */
@JsExport
interface InformationConceptGetResultDTO {
    val item: InformationConceptFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptGetResult(
    override val item: InformationConceptFlat?,
    override val graph: CccevFlatGraph
): InformationConceptGetResultDTO

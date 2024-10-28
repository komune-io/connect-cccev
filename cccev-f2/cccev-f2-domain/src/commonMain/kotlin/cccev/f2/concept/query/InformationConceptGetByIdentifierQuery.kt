package cccev.f2.concept.query

import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.concept.model.InformationConceptFlat
import cccev.f2.concept.model.InformationConceptFlatDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Get an information concept by its identifier.
 * @d2 function
 * @parent [cccev.core.concept.D2InformationConceptPage]
 */
typealias InformationConceptGetByIdentifierFunction
        = F2Function<InformationConceptGetByIdentifierQuery, InformationConceptGetByIdentifierResult>

/**
 * @d2 query
 * @parent [InformationConceptGetByIdentifierFunction]
 */
@JsExport
interface InformationConceptGetByIdentifierQueryDTO {
    /**
     * Identifier of the information concept to get.
     */
    val identifier: String
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptGetByIdentifierQuery(
    override val identifier: String
): InformationConceptGetByIdentifierQueryDTO

/**
 * @d2 result
 * @parent [InformationConceptGetByIdentifierFunction]
 */
@JsExport
interface InformationConceptGetByIdentifierResultDTO {
    val item: InformationConceptFlatDTO?
    val graph: CccevFlatGraphDTO
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptGetByIdentifierResult(
    override val item: InformationConceptFlat?,
    override val graph: CccevFlatGraph
): InformationConceptGetByIdentifierResultDTO

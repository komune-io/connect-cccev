package cccev.f2.evidencetype.model

import cccev.f2.concept.model.InformationConceptIdentifier
import cccev.f2.evidencetype.D2EvidenceTypePage
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * @d2 model
 * @parent [D2EvidenceTypePage]
 * @order 10
 */
@JsExport
interface EvidenceTypeFlatDTO {
    /**
     * Identifier of the evidence type.
     */
    val id: EvidenceTypeId

    /**
     * Name of the evidence type.
     * @example [cccev.s2.evidence.domain.model.EvidenceType.name]
     */
    val name: String

    val conceptIdentifiers: List<InformationConceptIdentifier>
}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeFlat(
    override val id: EvidenceTypeId,
    override val name: String,
    override val conceptIdentifiers: List<InformationConceptIdentifier>
): EvidenceTypeFlatDTO

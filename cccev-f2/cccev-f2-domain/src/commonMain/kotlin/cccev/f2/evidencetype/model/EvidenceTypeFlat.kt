package cccev.f2.evidencetype.model

import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.EvidenceTypeIdentifier
import cccev.dsl.model.InformationConceptIdentifier
import cccev.f2.evidencetype.D2EvidenceTypePage
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

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
     * Identifier of the evidence type.
     */
    val identifier: EvidenceTypeId

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
    override val identifier: EvidenceTypeIdentifier,
    override val name: String,
    override val conceptIdentifiers: List<InformationConceptIdentifier>
): EvidenceTypeFlatDTO

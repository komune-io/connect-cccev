package cccev.f2.evidencetypelist.model

import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.EvidenceTypeIdentifier
import cccev.dsl.model.EvidenceTypeListId
import cccev.dsl.model.EvidenceTypeListIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 model
 * @parent [cccev.dsl.model.d2.D2EvidenceTypeListPage]
 * @order 10
 */
@JsExport
interface EvidenceTypeListFlatDTO {
    /**
     * Identifier of the evidence type.
     */
    val id: EvidenceTypeListId
    /**
     * Identifier of the evidence type.
     */
    val identifier: EvidenceTypeListIdentifier

    /**
     * Name of the evidence type.
     * @example [cccev.dsl.model.EvidenceTypeList.name]
     */
    val name: String

    /**
     * description of the evidence type.
     * @example [cccev.dsl.model.EvidenceTypeList.description]
     */
    val description: String


    val specifiesEvidenceType: List<EvidenceTypeListIdentifier>?

}

/**
 * @d2 inherit
 */
@Serializable
data class EvidenceTypeListFlat(
    override val id: EvidenceTypeId,
    override val identifier: EvidenceTypeIdentifier,
    override val name: String,
    override val description: String,
    override val specifiesEvidenceType: List<EvidenceTypeListIdentifier>? = null,
): EvidenceTypeListFlatDTO

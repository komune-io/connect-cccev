package cccev.f2.unit.domain.model

import cccev.f2.unit.domain.D2DataUnitF2Page
import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.DataUnitIdentifier
import cccev.s2.unit.domain.DataUnitOptionIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * See [cccev.s2.unit.domain.model.DataUnit]
 * @d2 model
 * @parent [D2DataUnitF2Page]
 * @order 20
 */
@JsExport
interface DataUnitFlatDTO {
    /**
     * @ref [DataUnitDTO.id]
     */
    val id: DataUnitId

    /**
     * @ref [DataUnitDTO.identifier]
     */
    val identifier: DataUnitIdentifier

    /**
     * @ref [DataUnitDTO.name]
     */
    val name: String

    /**
     * @ref [DataUnitDTO.description]
     */
    val description: String

    /**
     * @ref [DataUnitDTO.notation]
     */
    val notation: String?

    /**
     * @ref [DataUnitDTO.type]
     */
    val type: String

    /**
     * @ref [DataUnitDTO.options]
     */
    val optionIdentifiers: List<DataUnitOptionIdentifier>?
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitFlat(
    override val id: DataUnitId,
    override val identifier: DataUnitIdentifier,
    override val name: String,
    override val description: String,
    override val notation: String? = null,
    override val type: String,
    override val optionIdentifiers: List<DataUnitOptionIdentifier>?
): DataUnitFlatDTO

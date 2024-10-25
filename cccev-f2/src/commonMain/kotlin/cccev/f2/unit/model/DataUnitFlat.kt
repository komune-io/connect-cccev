package cccev.f2.unit.model

import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import cccev.core.unit.model.DataUnitOptionIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * See [cccev.s2.unit.domain.model.DataUnit]
 * @d2 model
 * @parent [cccev.core.unit.D2DataUnitPage]
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
    val optionIdentifiers: List<DataUnitOptionIdentifier>
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
    override val optionIdentifiers: List<DataUnitOptionIdentifier>
): DataUnitFlatDTO

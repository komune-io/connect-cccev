package cccev.f2.unit.model

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitOptionIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

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
    val description: String?

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
    override val description: String? = null,
    override val notation: String? = null,
    override val type: String,
    override val optionIdentifiers: List<DataUnitOptionIdentifier>? = null
): DataUnitFlatDTO

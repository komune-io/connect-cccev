package cccev.f2.unit.model

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitOptionIdentifier
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * See [cccev.dsl.model.DataUnit]
 * @d2 model
 * @title DataUnitFlat
 * @parent [cccev.dsl.model.d2.D2DataUnitPage]
 */
@JsExport
interface DataUnitFlatDTO {
    /**
     * The unique id of the evidence.
     */
    val id: DataUnitId

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.identifier]
     */
    val identifier: DataUnitIdentifier

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.name]
     */
    val name: String

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.description]
     */
    val description: String?

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.notation]
     */
    val notation: String?

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.type]
     */
    val type: String

    /**
     * @ref [cccev.dsl.model.DataUnitDTO.options]
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

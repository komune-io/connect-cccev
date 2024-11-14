package cccev.f2.unit.command

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitType
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Create a new data unit.
 * @d2 function
 * @parent [cccev.f2.unit.D2DataUnitPage]
 */
typealias DataUnitCreateFunction = F2Function<DataUnitCreateCommand, DataUnitCreatedEvent>

/**
 * @d2 command
 * @parent [DataUnitCreateFunction]
 */
@Serializable
data class DataUnitCreateCommand(
    /**
     * The identifier of the data unit.
     * @example [cccev.dsl.model.DataUnitDTO.identifier]
     */
    val identifier: DataUnitIdentifier,

    /**
     * The name of the data unit.
     * @example [cccev.dsl.model.DataUnitDTO.name]
     */
    val name: String,

    /**
     * The description of the data unit.
     * @example [cccev.dsl.model.DataUnitDTO.description]
     */
    val description: String? = null,

    /**
     * The notation of the data unit.
     * @example [cccev.dsl.model.DataUnitDTO.notation]
     */
    val notation: String? = null,

    /**
     * The type of data used for this data unit.
     * @example [cccev.dsl.model.DataUnitDTO.type]
     */
    val type: DataUnitType,

    /**
     * @example null
     */
    val options: List<DataUnitOptionCommand>?
)

/**
 * @d2 event
 * @parent [DataUnitCreateFunction]
 */
@Serializable
data class DataUnitCreatedEvent(
    val id: DataUnitId,
)

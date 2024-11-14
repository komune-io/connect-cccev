package cccev.f2.unit.command

import cccev.dsl.model.DataUnitId
import cccev.f2.unit.D2DataUnitPage
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Update a data unit.
 * @d2 function
 * @parent [D2DataUnitPage]
 */
typealias DataUnitUpdateFunction = F2Function<DataUnitUpdateCommand, DataUnitUpdatedEvent>

/**
 * @d2 command
 * @parent [DataUnitUpdateFunction]
 */
@Serializable
data class DataUnitUpdateCommand(
    /**
     * The id of the data unit.
     * @example [cccev.dsl.model.DataUnitDTO.name]
     */
    val id: DataUnitId,

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
     * @ref [cccev.dsl.model.DataUnit.options]
     */
    val options: List<DataUnitOptionCommand>? = null,
)

/**
 * @d2 event
 * @parent [DataUnitUpdateFunction]
 */
@Serializable
data class DataUnitUpdatedEvent(
    val id: DataUnitId
)

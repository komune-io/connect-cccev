package cccev.core.unit.command

import cccev.core.unit.D2DataUnitPage
import cccev.core.unit.model.DataUnitId
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
     * @example [cccev.s2.unit.domain.model.DataUnit.name]
     */
    val id: DataUnitId,

    /**
     * The name of the data unit.
     * @example [cccev.s2.unit.domain.model.DataUnit.name]
     */
    val name: String,

    /**
     * The description of the data unit.
     * @example [cccev.s2.unit.domain.model.DataUnit.description]
     */
    val description: String,

    /**
     * The notation of the data unit.
     * @example [cccev.s2.unit.domain.model.DataUnit.notation]
     */
    val notation: String? = null,

    /**
     * @ref [cccev.s2.unit.domain.model.DataUnit.options]
     */
    val options: List<DataUnitOptionCommand>
)

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitUpdatedEvent(
    val id: DataUnitId
)

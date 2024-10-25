package cccev.core.unit.command

import cccev.core.unit.D2DataUnitPage
import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import cccev.core.unit.model.DataUnitType
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Create a new data unit.
 * @d2 function
 * @parent [D2DataUnitPage]
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
     * @example [cccev.s2.unit.domain.model.DataUnit.name]
     */
    val identifier: DataUnitIdentifier,

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
     * The type of data used for this data unit.
     * @example [cccev.s2.unit.domain.model.DataUnit.type]
     */
    val type: DataUnitType,

    /**
     * @ref [cccev.s2.unit.domain.model.DataUnit.options]
     */
    val options: List<DataUnitOptionCommand>
)

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitCreatedEvent(
    val id: DataUnitId,
)

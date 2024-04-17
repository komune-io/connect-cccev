package cccev.s2.unit.domain.model

import cccev.s2.unit.domain.D2DataUnitPage
import cccev.s2.unit.domain.DataUnitOptionId
import cccev.s2.unit.domain.DataUnitOptionIdentifier
import io.komune.fs.s2.file.domain.model.FilePath
import kotlinx.serialization.Serializable

/**
 * Represents a unit of data, such as a physical measurement, date, or number.
 * @d2 model
 * @parent [D2DataUnitPage]
 * @order 100
 */
@Serializable
data class DataUnitOption(
    /**
     * Technical ID of the option.
     */
    val id: DataUnitOptionId,

    /**
     * Unique identifier of the option.
     */
    val identifier: DataUnitOptionIdentifier,

    /**
     * The name of the option that will be displayed.
     * @example "Banana"
     */
    val name: String,

    /**
     * The value of the option that will saved and used (e.g. to evaluate requirements). \
     * This is a json representation of the actual value. The actual value must match the type of its associated DataUnit.
     * @example "banana"
     */
    val value: String,

    /**
     * The order in which the option should be displayed.
     * @example 0
     */
    val order: Int,

    /**
     * An optional icon to display next to the option when relevant.
     * @example null
     */
    val icon: FilePath?,

    /**
     * An optional hexadecimal color in which to display the option when relevant.
     * @example null
     */
    val color: String?
)

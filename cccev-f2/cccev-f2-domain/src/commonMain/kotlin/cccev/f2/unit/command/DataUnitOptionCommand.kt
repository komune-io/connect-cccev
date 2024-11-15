package cccev.f2.unit.command

import cccev.dsl.model.DataUnitOptionId
import cccev.dsl.model.DataUnitOptionIdentifier
import io.komune.fs.s2.file.domain.model.FilePath
import io.komune.fs.s2.file.domain.model.FilePathDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Represents a command for a data unit option with details such as identifier, name, value, etc.
 * @d2 command
 * @parent [cccev.dsl.model.d2.D2DataUnitPage]
 */
@JsExport
interface DataUnitOptionCommandDTO {
    /**
     * Represents the unique identifier for a data unit option.
     */
    val id: DataUnitOptionId?
    /**
     * Represents the unique identifier for a data unit option.
     */
    val identifier: DataUnitOptionIdentifier
    /**
     * The name of the data unit option.
     */
    val name: String
    /**
     * Represents the value for a data unit option.
     */
    val value: String
    /**
     * The order in which the data unit option should be displayed or processed.
     */
    val order: Int
    /**
     * Represents the file path of an icon associated with a data unit option.
     * It is used to store the path to the image file that serves as the icon for the data unit option.
     */
    val icon: FilePathDTO?
    /**
     * The color associated with a data unit option.
     * It can be used to visually distinguish different options.
     */
    val color: String?
}

/**
 * @d2 inherit
 */
@Serializable
data class DataUnitOptionCommand(
    override val id: DataUnitOptionId?,
    override val identifier: DataUnitOptionIdentifier,
    override val name: String,
    override val value: String,
    override val order: Int,
    override val icon: FilePath?,
    override val color: String?
): DataUnitOptionCommandDTO

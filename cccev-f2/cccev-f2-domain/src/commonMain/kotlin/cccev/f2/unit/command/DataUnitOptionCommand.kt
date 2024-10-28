package cccev.f2.unit.command

import cccev.dsl.model.DataUnitOptionId
import cccev.dsl.model.DataUnitOptionIdentifier
import io.komune.fs.s2.file.domain.model.FilePath
import io.komune.fs.s2.file.domain.model.FilePathDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
interface DataUnitOptionCommandDTO {
    val id: DataUnitOptionId?
    val identifier: DataUnitOptionIdentifier
    val name: String
    val value: String
    val order: Int
    val icon: FilePathDTO?
    val color: String?
}

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

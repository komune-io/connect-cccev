package cccev.f2.unit.model

import io.komune.fs.s2.file.domain.model.FilePath
import io.komune.fs.s2.file.domain.model.FilePathDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
interface DataUnitOptionDTO {
    val id: DataUnitOptionId
    val identifier: DataUnitOptionIdentifier
    val name: String
    val value: String
    val order: Int
    val icon: FilePathDTO?
    val color: String?
}

@Serializable
data class DataUnitOption(
    override val id: DataUnitOptionId,
    override val identifier: DataUnitOptionIdentifier,
    override val name: String,
    override val value: String,
    override val order: Int,
    override val icon: FilePath?,
    override val color: String?
): DataUnitOptionDTO

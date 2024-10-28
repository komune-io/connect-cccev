package cccev.dsl.model

import io.komune.fs.s2.file.domain.model.FilePath
import io.komune.fs.s2.file.domain.model.FilePathDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 hidden
 * @visual json "greatOption"
 */
typealias DataUnitOptionIdentifier = String

/**
 * @d2 hidden
 * @visual json "55355dfd-cbe2-4c2d-a3be-36f4298f484f"
 */
typealias DataUnitOptionId = String

@JsExport
interface DataUnitOptionDTO {
    val identifier: DataUnitOptionIdentifier
    val name: String
    val value: String
    val order: Int
    val icon: FilePathDTO?
    val color: String?
}

@Serializable
data class DataUnitOption(
    override val identifier: DataUnitOptionIdentifier,
    override val name: String,
    override val value: String,
    override val order: Int,
    override val icon: FilePath?,
    override val color: String?
): DataUnitOptionDTO

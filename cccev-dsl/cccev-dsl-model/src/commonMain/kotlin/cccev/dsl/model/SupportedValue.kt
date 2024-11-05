package cccev.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias SupportedValueId = String
typealias SupportedValueIdentifier = String

@JsExport
@JsName("SupportedValueDTO")
interface SupportedValueDTO {
    val identifier: SupportedValueIdentifier
    val value: String?
    val query: String?
    val providesValueFor: InformationConceptId
}

@Serializable
open class SupportedValue(
    override val identifier: SupportedValueIdentifier,
    override val value: String? = null,
    override val query: String? = null,
    override val providesValueFor: InformationConceptId
): SupportedValueDTO

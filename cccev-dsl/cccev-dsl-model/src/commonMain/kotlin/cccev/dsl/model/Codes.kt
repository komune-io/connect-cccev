@file:Suppress("MatchingDeclarationName")
package cccev.dsl.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@JsExport
@Serializable
open class Code {
    override fun toString(): String {
        return this::class.simpleName ?: "Unknown"
    }
}

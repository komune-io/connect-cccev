package cccev.dsl.model

import kotlin.js.JsExport

@JsExport
object DataUnitTypeValues {
    fun boolean() = DataUnitType.BOOLEAN.name
    fun date() = DataUnitType.DATE.name
    fun number() = DataUnitType.NUMBER.name
    fun string() = DataUnitType.STRING.name
}

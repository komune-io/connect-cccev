package cccev.s2.unit.domain

import cccev.s2.unit.domain.command.DataUnitCreateCommand
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2
import s2.dsl.automate.model.WithS2Id
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Identifier of a DataUnit
 * @visual json "aeb2c258-9eab-4e55-9e52-77db89aaabfb"
 * @parent [D2DataUnitPage]
 * @order 15
 */
typealias DataUnitId = String

val s2DataUnit = s2 {
	name = "DataUnitS2"
	init<DataUnitCreateCommand> {
		to = DataUnitState.EXISTS
		role = EditorRole
	}
}

enum class DataUnitState(override var position: Int): S2State {
	EXISTS(0)
}

object EditorRole: S2Role
object AuditorRole: S2Role

@JsExport
@JsName("DataUnitInitCommand")
interface DataUnitInitCommand: S2InitCommand

@JsExport
@JsName("DataUnitCommand")
interface DataUnitCommand: S2Command<DataUnitId>

@JsExport
@JsName("DataUnitEvent")
interface DataUnitEvent: Evt, WithS2Id<DataUnitId>

package cccev.s2.requirement.domain

import cccev.s2.requirement.domain.command.RequirementCreateCommand
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2
import kotlin.js.JsExport
import kotlin.js.JsName
import s2.dsl.automate.model.WithS2Id

/**
 * @d2 hidden
 * @visual json "5b3cc623-52bc-409f-a048-02a9b5f433d6"
 */
typealias RequirementId = String

val s2Requirement = s2 {
	name = "RequirementS2"
	init<RequirementCreateCommand> {
		to = RequirementState.CREATED
		role = EditorRole
	}
}

/**
 * @d2 model
 * @parent [D2RequirementPage]
 * @order 15
 */
enum class RequirementState(override var position: Int): S2State {
	CREATED(0)
}

object EditorRole: S2Role
object AuditorRole: S2Role

@JsExport
@JsName("RequirementInitCommand")
interface RequirementInitCommand: S2InitCommand

@JsExport
@JsName("RequirementCommand")
interface RequirementCommand: S2Command<RequirementId>

@JsExport
@JsName("RequirementEvent")
interface RequirementEvent: Evt, WithS2Id<RequirementId>

package cccev.s2.evidence.domain

import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdateCommand
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2
import s2.dsl.automate.model.WithS2Id
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Identifier of an EvidenceTypeList
 * @visual json "23492fe0-1920-4951-863f-5fec8ee58907"
 * @parent [D2EvidenceTypePage]
 * @order 105
 */
typealias EvidenceTypeListId = String

val s2EvidenceTypeList = s2 {
	name = "EvidenceTypeListS2"
	init<EvidenceTypeListCreateCommand> {
		to = EvidenceTypeListState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListUpdateCommand> {
		states += EvidenceTypeListState.EXISTS
		role = EditorRole
	}
}

enum class EvidenceTypeListState(override var position: Int): S2State {
	EXISTS(0)
}

@JsExport
@JsName("EvidenceTypeListInitCommand")
interface EvidenceTypeListInitCommand: S2InitCommand

@JsExport
@JsName("EvidenceTypeListCommand")
interface EvidenceTypeListCommand: S2Command<EvidenceTypeListId>

@JsExport
@JsName("EvidenceTypeListEvent")
interface EvidenceTypeListEvent: Evt, WithS2Id<EvidenceTypeListId>

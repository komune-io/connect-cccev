package cccev.s2.evidence.domain

import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemoveEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdateCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListUpdatedEvent
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2Sourcing
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

val s2EvidenceTypeList = s2Sourcing {
	name = "EvidenceTypeListS2"
	init<EvidenceTypeListCreateCommand, EvidenceTypeListCreatedEvent> {
		to = EvidenceTypeListState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListUpdateCommand, EvidenceTypeListUpdatedEvent> {
		states += EvidenceTypeListState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListAddEvidenceTypesCommand, EvidenceTypeListAddedEvidenceTypesEvent> {
		states += EvidenceTypeListState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListRemoveEvidenceTypesCommand, EvidenceTypeListRemovedEvidenceTypesEvent> {
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
interface EvidenceTypeListEvent: Evt, WithS2Id<EvidenceTypeListId>, WithId<EvidenceTypeListId> {
	override fun s2Id() = id
}

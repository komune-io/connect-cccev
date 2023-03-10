package cccev.s2.evidence.domain

import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemoveEvidenceTypesCommand
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreateCommand
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreatedEvent
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2
import s2.dsl.automate.builder.s2Sourcing
import s2.dsl.automate.model.WithS2Id
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Identifier of an EvidenceType
 * @visual json "0aec761a-1696-4f9a-91b9-b4c6b006762b"
 * @parent [D2EvidenceTypePage]
 * @order 15
 */
typealias EvidenceTypeId = String

val s2EvidenceType = s2Sourcing {
	name = "EvidenceTypeS2"
	init<EvidenceTypeCreateCommand, EvidenceTypeCreatedEvent> {
		to = EvidenceTypeState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListAddEvidenceTypesCommand, EvidenceTypeListAddedEvidenceTypesEvent> {
		states += EvidenceTypeState.EXISTS
		role = EditorRole
	}
	selfTransaction<EvidenceTypeListRemoveEvidenceTypesCommand, EvidenceTypeListRemovedEvidenceTypesEvent> {
		states += EvidenceTypeState.EXISTS
		role = EditorRole
	}
}

enum class EvidenceTypeState(override var position: Int): S2State {
	EXISTS(0)
}

object EditorRole: S2Role
object AuditorRole: S2Role

@JsExport
@JsName("EvidenceTypeInitCommand")
interface EvidenceTypeInitCommand: S2InitCommand

@JsExport
@JsName("EvidenceTypeCommand")
interface EvidenceTypeCommand: S2Command<EvidenceTypeId>

@JsExport
@JsName("EvidenceTypeEvent")
interface EvidenceTypeEvent: Evt, WithS2Id<EvidenceTypeId>

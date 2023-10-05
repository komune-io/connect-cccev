package cccev.s2.concept.domain

import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import kotlin.js.JsExport
import kotlin.js.JsName
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.builder.s2Sourcing
import s2.dsl.automate.model.WithS2Id

/**
 * Unique identifier of an information concept.
 * @d2 model
 * @parent [D2InformationConceptPage]
 * @order 15
 * @visual json "cdfca416-c284-4ef9-8591-31405c8c2acf"
 */
typealias InformationConceptId = String
typealias InformationConceptIdentifier = String

val s2InformationConcept = s2Sourcing {
	name = "InformationConceptS2"
	init<InformationConceptCreateCommand, InformationConceptCreatedEvent> {
		to = InformationConceptState.EXISTS
		role = EditorRole
	}
}

enum class InformationConceptState(override var position: Int): S2State {
	EXISTS(0)
}

object EditorRole: S2Role
object AuditorRole: S2Role

@JsExport
@JsName("InformationConceptInitCommand")
interface InformationConceptInitCommand: S2InitCommand

@JsExport
@JsName("InformationConceptCommand")
interface InformationConceptCommand: S2Command<InformationConceptId>

@JsExport
@JsName("InformationConceptEvent")
interface InformationConceptEvent: Evt, WithS2Id<InformationConceptId>

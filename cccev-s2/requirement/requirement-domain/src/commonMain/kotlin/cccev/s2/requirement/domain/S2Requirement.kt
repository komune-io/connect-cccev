package cccev.s2.requirement.domain

import cccev.s2.requirement.domain.command.RequirementAddConceptsCommand
import cccev.s2.requirement.domain.command.RequirementAddEvidenceTypeListsCommand
import cccev.s2.requirement.domain.command.RequirementAddRequirementsCommand
import cccev.s2.requirement.domain.command.RequirementAddedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementAddedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementAddedRequirementsEvent
import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementRemoveConceptsCommand
import cccev.s2.requirement.domain.command.RequirementRemoveEvidenceTypeListsCommand
import cccev.s2.requirement.domain.command.RequirementRemoveRequirementsCommand
import cccev.s2.requirement.domain.command.RequirementRemovedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedRequirementsEvent
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.WithId
import s2.dsl.automate.builder.s2Sourcing
import kotlin.js.JsExport
import kotlin.js.JsName
import s2.dsl.automate.model.WithS2Id

/**
 * @d2 hidden
 * @visual json "5b3cc623-52bc-409f-a048-02a9b5f433d6"
 */
typealias RequirementId = String

val s2Requirement = s2Sourcing {
	name = "RequirementS2"
	init<RequirementCreateCommand, RequirementCreatedEvent> {
		to = RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementAddRequirementsCommand, RequirementAddedRequirementsEvent> {
		states += RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementRemoveRequirementsCommand, RequirementRemovedRequirementsEvent> {
		states += RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementAddConceptsCommand, RequirementAddedConceptsEvent> {
		states += RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementRemoveConceptsCommand, RequirementRemovedConceptsEvent> {
		states += RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementAddEvidenceTypeListsCommand, RequirementAddedEvidenceTypeListsEvent> {
		states += RequirementState.CREATED
		role = EditorRole
	}
	selfTransaction<RequirementRemoveEvidenceTypeListsCommand, RequirementRemovedEvidenceTypeListsEvent> {
		states += RequirementState.CREATED
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
interface RequirementEvent: Evt, WithS2Id<RequirementId>, WithId<RequirementId> {
	override fun s2Id() = id
}

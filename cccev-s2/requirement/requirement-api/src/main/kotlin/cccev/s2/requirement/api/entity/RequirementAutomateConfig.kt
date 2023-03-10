package cccev.s2.requirement.api.entity

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementEvolver
import cccev.projection.api.entity.requirement.RequirementSnapRepository
import cccev.s2.requirement.domain.RequirementEvent
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.command.RequirementAddedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementAddedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementAddedRequirementsEvent
import cccev.s2.requirement.domain.command.RequirementCreatedEvent
import cccev.s2.requirement.domain.command.RequirementRemovedConceptsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedEvidenceTypeListsEvent
import cccev.s2.requirement.domain.command.RequirementRemovedRequirementsEvent
import cccev.s2.requirement.domain.command.RequirementUpdatedEvent
import cccev.s2.requirement.domain.s2Requirement
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.ssm.S2SourcingSsmAdapter
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.from
import ssm.sdk.sign.extention.loadFromFile
import kotlin.reflect.KClass

@Configuration
class RequirementAutomateConfig(
	automateExecutor: RequirementAutomateExecutor,
	requirementEvolver: RequirementEvolver,
	requirementSnapRepository: RequirementSnapRepository,
): S2SourcingSsmAdapter<
		RequirementEntity,
		RequirementState,
		RequirementEvent,
		RequirementId,
		RequirementAutomateExecutor
	>(automateExecutor, requirementEvolver, requirementSnapRepository) {
	override fun automate() = s2Requirement
	override fun entityType(): KClass<RequirementEvent> {
		return RequirementEvent::class
	}

	override var permisive: Boolean = true

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			polymorphic(RequirementEvent::class) {
				subclass(RequirementCreatedEvent::class, RequirementCreatedEvent.serializer())
				subclass(RequirementUpdatedEvent::class, RequirementUpdatedEvent.serializer())
				subclass(RequirementAddedRequirementsEvent::class, RequirementAddedRequirementsEvent.serializer())
				subclass(RequirementRemovedRequirementsEvent::class, RequirementRemovedRequirementsEvent.serializer())
				subclass(RequirementAddedConceptsEvent::class, RequirementAddedConceptsEvent.serializer())
				subclass(RequirementRemovedConceptsEvent::class, RequirementRemovedConceptsEvent.serializer())
				subclass(RequirementAddedEvidenceTypeListsEvent::class, RequirementAddedEvidenceTypeListsEvent.serializer())
				subclass(RequirementRemovedEvidenceTypeListsEvent::class, RequirementRemovedEvidenceTypeListsEvent.serializer())
			}
		}
	}

	override fun chaincodeUri(): ChaincodeUri {
		return ChaincodeUri.from(
			channelId = "sandbox",
			chaincodeId = "ssm",
		)
	}

	override fun signerAgent(): Agent {
		return Agent.loadFromFile("ssm-admin","user/ssm-admin")
	}
}

@Service
class RequirementAutomateExecutor: S2AutomateDeciderSpring<RequirementEntity, RequirementState, RequirementEvent, RequirementId>()

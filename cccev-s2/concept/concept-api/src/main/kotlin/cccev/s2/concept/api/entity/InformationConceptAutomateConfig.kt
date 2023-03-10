package cccev.s2.concept.api.entity

import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.projection.api.entity.concept.InformationConceptEvolver
import cccev.projection.api.entity.concept.InformationConceptSnapRepository
import cccev.s2.concept.domain.InformationConceptEvent
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.concept.domain.InformationConceptState
import cccev.s2.concept.domain.command.InformationConceptCreatedEvent
import cccev.s2.concept.domain.s2InformationConcept
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
class InformationConceptAutomateConfig(
	automateExecutor: InformationConceptAutomateExecutor,
	informationConceptEvolver: InformationConceptEvolver,
	informationConceptSnapRepository: InformationConceptSnapRepository,
): S2SourcingSsmAdapter<
		InformationConceptEntity,
		InformationConceptState,
		InformationConceptEvent,
		InformationConceptId,
		InformationConceptAutomateExecutor
	>(automateExecutor, informationConceptEvolver, informationConceptSnapRepository) {
	override fun automate() = s2InformationConcept
	override fun entityType(): KClass<InformationConceptEvent> {
		return InformationConceptEvent::class
	}

	override var permisive: Boolean = true

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			polymorphic(InformationConceptEvent::class) {
				subclass(InformationConceptCreatedEvent::class, InformationConceptCreatedEvent.serializer())
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
class InformationConceptAutomateExecutor: S2AutomateDeciderSpring<InformationConceptEntity, InformationConceptState, InformationConceptEvent, InformationConceptId>()

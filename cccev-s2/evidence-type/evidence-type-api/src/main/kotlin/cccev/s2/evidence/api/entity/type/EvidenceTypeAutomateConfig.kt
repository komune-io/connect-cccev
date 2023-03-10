package cccev.s2.evidence.api.entity.type

import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.projection.api.entity.evidencetype.EvidenceTypeEvolver
import cccev.projection.api.entity.evidencetype.EvidenceTypeSnapRepository
import cccev.s2.evidence.domain.EvidenceTypeEvent
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeState
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreatedEvent
import cccev.s2.evidence.domain.s2EvidenceType
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
class EvidenceTypeAutomateConfig(
	automateExecutor: EvidenceTypeAutomateExecutor,
	evidenceTypeEvolver: EvidenceTypeEvolver,
	evidenceTypeSnapRepository: EvidenceTypeSnapRepository,
): S2SourcingSsmAdapter<
		EvidenceTypeEntity,
		EvidenceTypeState,
		EvidenceTypeEvent,
		EvidenceTypeId,
		EvidenceTypeAutomateExecutor
	>(automateExecutor, evidenceTypeEvolver, evidenceTypeSnapRepository) {
	override fun automate() = s2EvidenceType
	override fun entityType(): KClass<EvidenceTypeEvent> {
		return EvidenceTypeEvent::class
	}

	override var permisive: Boolean = true

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			polymorphic(EvidenceTypeEvent::class) {
				subclass(EvidenceTypeCreatedEvent::class, EvidenceTypeCreatedEvent.serializer())
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
class EvidenceTypeAutomateExecutor: S2AutomateDeciderSpring<EvidenceTypeEntity, EvidenceTypeState, EvidenceTypeEvent, EvidenceTypeId>()

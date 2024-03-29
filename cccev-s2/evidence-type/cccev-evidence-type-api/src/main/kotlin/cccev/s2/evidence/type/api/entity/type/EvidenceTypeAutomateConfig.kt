package cccev.s2.evidence.type.api.entity.type

import cccev.projection.api.entity.evidencetype.EvidenceTypeEntity
import cccev.projection.api.entity.evidencetype.EvidenceTypeEvolver
import cccev.projection.api.entity.evidencetype.EvidenceTypeSnapRepository
import cccev.s2.evidence.type.domain.EvidenceTypeEvent
import cccev.s2.evidence.type.domain.EvidenceTypeId
import cccev.s2.evidence.type.domain.EvidenceTypeState
import cccev.s2.evidence.type.domain.command.type.EvidenceTypeCreatedEvent
import cccev.s2.evidence.type.domain.s2EvidenceType
import kotlin.reflect.KClass
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
			classDiscriminator = "class"
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


	override fun preventOptimisticLocking(): Boolean = true

}

@Service
class EvidenceTypeAutomateExecutor: S2AutomateDeciderSpring<EvidenceTypeEntity, EvidenceTypeState, EvidenceTypeEvent, EvidenceTypeId>()

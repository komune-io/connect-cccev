package cccev.s2.evidence.type.api.entity.list

import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEntity
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEvolver
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListSnapRepository
import cccev.s2.evidence.type.domain.EvidenceTypeListEvent
import cccev.s2.evidence.type.domain.EvidenceTypeListId
import cccev.s2.evidence.type.domain.EvidenceTypeListState
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListAddedEvidenceTypesEvent
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListCreatedEvent
import cccev.s2.evidence.type.domain.command.list.EvidenceTypeListRemovedEvidenceTypesEvent
import cccev.s2.evidence.type.domain.s2EvidenceTypeList
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
class EvidenceTypeListAutomateConfig(
    automateExecutor: EvidenceTypeListAutomateExecutor,
    evidenceTypeListEvolver: EvidenceTypeListEvolver,
    evidenceTypeListSnapRepository: EvidenceTypeListSnapRepository,
): S2SourcingSsmAdapter<
		EvidenceTypeListEntity,
		EvidenceTypeListState,
		EvidenceTypeListEvent,
		EvidenceTypeListId,
        EvidenceTypeListAutomateExecutor
	>(automateExecutor, evidenceTypeListEvolver, evidenceTypeListSnapRepository) {
	override fun automate() = s2EvidenceTypeList
	override fun entityType(): KClass<EvidenceTypeListEvent> {
		return EvidenceTypeListEvent::class
	}

	override var permisive: Boolean = true

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			classDiscriminator = "class"
			polymorphic(EvidenceTypeListEvent::class) {
				subclass(EvidenceTypeListCreatedEvent::class, EvidenceTypeListCreatedEvent.serializer())
				subclass(EvidenceTypeListAddedEvidenceTypesEvent::class, EvidenceTypeListAddedEvidenceTypesEvent.serializer())
				subclass(EvidenceTypeListRemovedEvidenceTypesEvent::class, EvidenceTypeListRemovedEvidenceTypesEvent.serializer())
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
class EvidenceTypeListAutomateExecutor
	: S2AutomateDeciderSpring<
		EvidenceTypeListEntity,
		EvidenceTypeListState,
		EvidenceTypeListEvent,
		EvidenceTypeListId>()

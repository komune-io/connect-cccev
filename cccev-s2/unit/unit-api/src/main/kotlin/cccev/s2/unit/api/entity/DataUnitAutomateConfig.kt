package cccev.s2.unit.api.entity

import cccev.projection.api.entity.unit.DataUnitEntity
import cccev.projection.api.entity.unit.DataUnitEvolver
import cccev.projection.api.entity.unit.DataUnitSnapRepository
import cccev.s2.unit.domain.DataUnitEvent
import cccev.s2.unit.domain.DataUnitId
import cccev.s2.unit.domain.DataUnitState
import cccev.s2.unit.domain.command.DataUnitCreatedEvent
import cccev.s2.unit.domain.s2DataUnit
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
class DataUnitAutomateConfig(
	automateExecutor: DataUnitAutomateExecutor,
	dataUnitEvolver: DataUnitEvolver,
	dataUnitSnapRepository: DataUnitSnapRepository,
): S2SourcingSsmAdapter<
		DataUnitEntity,
		DataUnitState,
		DataUnitEvent,
		DataUnitId,
		DataUnitAutomateExecutor
	>(automateExecutor, dataUnitEvolver, dataUnitSnapRepository) {
	override fun automate() = s2DataUnit
	override fun entityType(): KClass<DataUnitEvent> {
		return DataUnitEvent::class
	}

	override fun json(): Json = Json {
		serializersModule = SerializersModule {
			polymorphic(DataUnitEvent::class) {
				subclass(DataUnitCreatedEvent::class, DataUnitCreatedEvent.serializer())
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
class DataUnitAutomateExecutor: S2AutomateDeciderSpring<DataUnitEntity, DataUnitState, DataUnitEvent, DataUnitId>()

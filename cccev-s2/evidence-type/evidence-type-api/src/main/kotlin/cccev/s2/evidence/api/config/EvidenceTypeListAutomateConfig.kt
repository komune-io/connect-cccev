package cccev.s2.evidence.api.config

import cccev.s2.evidence.api.entity.list.EvidenceTypeListEntity
import cccev.s2.evidence.api.entity.list.EvidenceTypeListRepository
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.EvidenceTypeListState
import cccev.s2.evidence.domain.s2EvidenceTypeList
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.spring.automate.data.S2SpringDataReactiveConfigurerAdapter
import s2.spring.automate.executor.S2AutomateExecutorSpring

@Configuration
class EvidenceTypeListAutomateConfig(
	private val automateExecutor: EvidenceTypeListAutomateExecutor,
	repository: EvidenceTypeListRepository
): S2SpringDataReactiveConfigurerAdapter<
		EvidenceTypeListState,
		EvidenceTypeListId,
		EvidenceTypeListEntity,
		EvidenceTypeListAutomateExecutor
		>(repository)
{
	override fun automate() = s2EvidenceTypeList
	override fun executor() = automateExecutor
}

@Service
class EvidenceTypeListAutomateExecutor: S2AutomateExecutorSpring<EvidenceTypeListState, EvidenceTypeListId, EvidenceTypeListEntity>()

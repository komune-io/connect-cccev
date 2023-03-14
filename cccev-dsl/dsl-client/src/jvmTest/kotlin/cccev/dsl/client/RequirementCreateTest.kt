package cccev.dsl.client

import cccev.dsl.client.requirement.LocalConsultationProtocolPreparationRequirements
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class RequirementCreateTest {
//    @Test
    fun shouldCreateLocalConsultation(): Unit = runBlocking {
        val client = CCCEVClient("http://localhost:8083")
        client.requirementCreate(LocalConsultationProtocolPreparationRequirements.asFlow()).onEach {
            println("Created requirement: ${it.id}")
        }.collect()
    }

}

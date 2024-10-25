package cccev.test.f2.requirement.command

import cccev.core.requirement.command.RequirementAddConceptsCommand
import cccev.core.requirement.entity.RequirementRepository
import cccev.f2.requirement.RequirementEndpoint
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.f2.requirement.data.requirement
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtractList

class RequirementAddConceptsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementEndpoint: RequirementEndpoint

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementAddConceptsCommand

    init {
        DataTableType(::requirementAddConceptsParams)
        DataTableType(::requirementAssertParams)

        When("I add information concepts to the requirement:") { params: RequirementAddConceptsParams ->
            step {
                addConcepts(params)
            }
        }

        Given("Some information concepts are added to the requirement:") { params: RequirementAddConceptsParams ->
            step {
                addConcepts(params)
            }
        }

        Then("The requirement should contain the information concepts") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .hasConcepts(command.conceptIds)
            }
        }

        Then("The requirement should contain the information concepts:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val conceptIds = params.concepts.map(context.conceptIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .hasConcepts(conceptIds)
            }
        }
    }

    private suspend fun addConcepts(params: RequirementAddConceptsParams) {
        command = RequirementAddConceptsCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            conceptIds = params.concepts.map { context.conceptIds[it] ?: it }
        )
        command.invokeWith(requirementEndpoint.requirementAddConcepts())
    }

    private fun requirementAddConceptsParams(entry: Map<String, String>) = RequirementAddConceptsParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        concepts = entry.safeExtractList("concepts"),
    )

    private data class RequirementAddConceptsParams(
        val identifier: TestContextKey,
        val concepts: List<TestContextKey>
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        concepts = entry.safeExtractList("concepts"),
    )

    private data class RequirementAssertParams(
        val identifier: TestContextKey,
        val concepts: List<TestContextKey>
    )
}

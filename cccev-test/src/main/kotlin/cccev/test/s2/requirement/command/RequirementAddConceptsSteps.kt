package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementAddConceptsCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RequirementAddConceptsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

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
        requirementAggregateService.addConcepts(command)
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

package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementRemoveConceptsCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RequirementRemoveConceptsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementRemoveConceptsCommand

    init {
        DataTableType(::requirementRemoveConceptsParams)
        DataTableType(::requirementAssertParams)

        When("I remove information concepts from the requirement:") { params: RequirementRemoveConceptsParams ->
            step {
                removeConcepts(params)
            }
        }

        Given("Some information concepts are removed from the requirement:") { params: RequirementRemoveConceptsParams ->
            step {
                removeConcepts(params)
            }
        }

        Then("The requirement should not contain the information concepts") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .doesNotHaveConcepts(command.conceptIds)
            }
        }

        Then("The requirement should not contain the information concepts:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val conceptIds = params.concepts.map(context.conceptIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .doesNotHaveConcepts(conceptIds)
            }
        }
    }

    private suspend fun removeConcepts(params: RequirementRemoveConceptsParams) {
        command = RequirementRemoveConceptsCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            conceptIds = params.concepts.map { context.conceptIds[it] ?: it }
        )
        requirementAggregateService.removeConcepts(command)
    }

    private fun requirementRemoveConceptsParams(entry: Map<String, String>) = RequirementRemoveConceptsParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        concepts = entry.safeExtractList("concepts"),
    )

    private data class RequirementRemoveConceptsParams(
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

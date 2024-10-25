package cccev.test.f2.requirement.command

import cccev.core.requirement.command.RequirementRemoveConceptsCommand
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

class RequirementRemoveConceptsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementEndpoint: RequirementEndpoint

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
        command.invokeWith(requirementEndpoint.requirementRemoveConcepts())
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

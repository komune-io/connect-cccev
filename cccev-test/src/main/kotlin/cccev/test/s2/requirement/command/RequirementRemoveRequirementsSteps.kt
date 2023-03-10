package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementRemoveRequirementsCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RequirementRemoveRequirementsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementRemoveRequirementsCommand

    init {
        DataTableType(::requirementRemoveRequirementParams)
        DataTableType(::requirementAssertParams)

        When("I remove sub-requirements from the requirement:") { params: RequirementRemoveRequirementParams ->
            step {
                removeRequirement(params)
            }
        }

        Given("Some sub-requirements are removed from the requirement:") { params: RequirementRemoveRequirementParams ->
            step {
                removeRequirement(params)
            }
        }

        Then("The requirement should not contain the sub-requirements") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .doesNotHaveRequirements(command.requirementIds)
            }
        }

        Then("The requirement should not contain the sub-requirements:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val subRequirementIds = params.requirements.map(context.requirementIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .doesNotHaveRequirements(subRequirementIds)
            }
        }
    }

    private suspend fun removeRequirement(params: RequirementRemoveRequirementParams) {
        command = RequirementRemoveRequirementsCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            requirementIds = params.requirements.map { context.requirementIdentifiers[it] ?: it }
        )
        requirementAggregateService.removeRequirements(command)
    }

    private fun requirementRemoveRequirementParams(entry: Map<String, String>) = RequirementRemoveRequirementParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        requirements = entry.safeExtractList("requirements"),
    )

    private data class RequirementRemoveRequirementParams(
        val identifier: TestContextKey,
        val requirements: List<TestContextKey>
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        requirements = entry.safeExtractList("requirements"),
    )

    private data class RequirementAssertParams(
        val identifier: TestContextKey,
        val requirements: List<TestContextKey>
    )
}

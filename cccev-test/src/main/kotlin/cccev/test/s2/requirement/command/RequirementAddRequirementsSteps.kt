package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementAddRequirementsCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RequirementAddRequirementsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementAddRequirementsCommand

    init {
        DataTableType(::requirementAddRequirementsParams)
        DataTableType(::requirementAssertParams)

        When("I add sub-requirements to the requirement:") { params: RequirementAddRequirementsParams ->
            step {
                addRequirement(params)
            }
        }

        Given("Some sub-requirements are added to the requirement:") { params: RequirementAddRequirementsParams ->
            step {
                addRequirement(params)
            }
        }

        Then("The requirement should contain the sub-requirements") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .hasRequirements(command.requirementIds)
            }
        }

        Then("The requirement should contain the sub-requirements:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val subRequirementIds = params.requirements.map(context.requirementIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .hasRequirements(subRequirementIds)
            }
        }
    }

    private suspend fun addRequirement(params: RequirementAddRequirementsParams) {
        command = RequirementAddRequirementsCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            requirementIds = params.requirements.map { context.requirementIdentifiers[it] ?: it }
        )
        requirementAggregateService.addRequirements(command)
    }

    private fun requirementAddRequirementsParams(entry: Map<String, String>) = RequirementAddRequirementsParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        requirements = entry.safeExtractList("requirements"),
    )

    private data class RequirementAddRequirementsParams(
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

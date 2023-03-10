package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.command.RequirementRemoveEvidenceTypeListsCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RequirementRemoveEvidenceTypeListsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementRemoveEvidenceTypeListsCommand

    init {
        DataTableType(::requirementRemoveEvidenceTypeListsParams)
        DataTableType(::requirementAssertParams)

        When("I remove evidence type lists from the requirement:") { params: RequirementRemoveEvidenceTypeListsParams ->
            step {
                removeEvidenceTypeLists(params)
            }
        }

        Given("Some evidence type lists are removed from the requirement:") { params: RequirementRemoveEvidenceTypeListsParams ->
            step {
                removeEvidenceTypeLists(params)
            }
        }

        Then("The requirement should not contain the evidence type lists") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .doesNotHaveEvidenceTypeLists(command.evidenceTypeListIds)
            }
        }

        Then("The requirement should not contain the evidence type lists:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val evidenceTypeListIds = params.evidenceTypeLists.map(context.evidenceTypeListIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .doesNotHaveEvidenceTypeLists(evidenceTypeListIds)
            }
        }
    }

    private suspend fun removeEvidenceTypeLists(params: RequirementRemoveEvidenceTypeListsParams) {
        command = RequirementRemoveEvidenceTypeListsCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            evidenceTypeListIds = params.evidenceTypeLists.map { context.evidenceTypeListIds[it] ?: it }
        )
        requirementAggregateService.removeEvidenceTypeLists(command)
    }

    private fun requirementRemoveEvidenceTypeListsParams(entry: Map<String, String>) = RequirementRemoveEvidenceTypeListsParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        evidenceTypeLists = entry.safeExtractList("evidenceTypeLists"),
    )

    private data class RequirementRemoveEvidenceTypeListsParams(
        val identifier: TestContextKey,
        val evidenceTypeLists: List<TestContextKey>
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        evidenceTypeLists = entry.safeExtractList("evidenceTypeLists"),
    )

    private data class RequirementAssertParams(
        val identifier: TestContextKey,
        val evidenceTypeLists: List<TestContextKey>
    )
}

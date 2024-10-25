package cccev.test.f2.requirement.command

import cccev.core.requirement.command.RequirementRemoveEvidenceTypesCommand
import cccev.core.requirement.entity.RequirementRepository
import cccev.f2.requirement.RequirementEndpoint
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.EvidenceTypeKey
import cccev.test.RequirementKey
import cccev.test.f2.requirement.data.requirement
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.parser.safeExtractList

class RequirementRemoveEvidenceTypesSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementEndpoint: RequirementEndpoint

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementRemoveEvidenceTypesCommand

    init {
        DataTableType(::requirementRemoveEvidenceTypesParams)
        DataTableType(::requirementAssertParams)

        When("I remove evidence types from the requirement:") { params: RequirementRemoveEvidenceTypesParams ->
            step {
                removeEvidenceTypes(params)
            }
        }

        Given("Some evidence types are removed from the requirement:") { params: RequirementRemoveEvidenceTypesParams ->
            step {
                removeEvidenceTypes(params)
            }
        }

        Then("The requirement should not contain the evidence types") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .doesNotHaveEvidenceTypes(command.evidenceTypeIds)
            }
        }

        Then("The requirement should not contain the evidence types:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val evidenceTypeIds = params.evidenceTypes.map(context.evidenceTypeIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .doesNotHaveEvidenceTypes(evidenceTypeIds)
            }
        }
    }

    private suspend fun removeEvidenceTypes(params: RequirementRemoveEvidenceTypesParams) {
        command = RequirementRemoveEvidenceTypesCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            evidenceTypeIds = params.evidenceTypes.map { context.evidenceTypeIds[it] ?: it },
            evidenceValidatingCondition = null
        )
        command.invokeWith(requirementEndpoint.requirementRemoveEvidenceTypes())
    }

    private fun requirementRemoveEvidenceTypesParams(entry: Map<String, String>) = RequirementRemoveEvidenceTypesParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class RequirementRemoveEvidenceTypesParams(
        val identifier: RequirementKey,
        val evidenceTypes: List<EvidenceTypeKey>
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class RequirementAssertParams(
        val identifier: RequirementKey,
        val evidenceTypes: List<EvidenceTypeKey>
    )
}

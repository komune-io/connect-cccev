package cccev.test.f2.requirement.command

import cccev.core.requirement.command.RequirementAddEvidenceTypesCommand
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

class RequirementAddEvidenceTypesSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementEndpoint: RequirementEndpoint

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementAddEvidenceTypesCommand

    init {
        DataTableType(::requirementAddEvidenceTypesParams)
        DataTableType(::requirementAssertParams)

        When("I add evidence types to the requirement:") { params: RequirementAddEvidenceTypesParams ->
            step {
                addEvidenceTypes(params)
            }
        }

        Given("Some evidence types are added to the requirement:") { params: RequirementAddEvidenceTypesParams ->
            step {
                addEvidenceTypes(params)
            }
        }

        Then("The requirement should contain the evidence types") {
            step {
                AssertionBdd.requirement(requirementRepository).assertThatId(command.id)
                    .hasEvidenceTypes(command.evidenceTypeIds)
            }
        }

        Then("The requirement should contain the evidence types:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val evidenceTypeIds = params.evidenceTypes.map(context.evidenceTypeIds::safeGet)
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId)
                    .hasEvidenceTypes(evidenceTypeIds)
            }
        }
    }

    private suspend fun addEvidenceTypes(params: RequirementAddEvidenceTypesParams) {
        command = RequirementAddEvidenceTypesCommand(
            id = context.requirementIds[params.identifier] ?: params.identifier,
            evidenceTypeIds = params.evidenceTypes.map { context.evidenceTypeIds[it] ?: it },
            evidenceValidatingCondition = null
        )
        command.invokeWith(requirementEndpoint.requirementAddEvidenceTypes())
    }

    private fun requirementAddEvidenceTypesParams(entry: Map<String, String>) = RequirementAddEvidenceTypesParams(
        identifier = entry["identifier"] ?: context.requirementIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class RequirementAddEvidenceTypesParams(
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

package cccev.test.s2.evidenceTypeList.command

import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.api.EvidenceTypeAggregateService
import cccev.s2.evidence.domain.command.list.EvidenceTypeListAddEvidenceTypesCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.evidenceTypeList.data.evidenceTypeList
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class EvidenceTypeListAddEvidenceTypesSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var evidenceTypeAggregateService: EvidenceTypeAggregateService

    @Autowired
    private lateinit var evidenceTypeListRepository: EvidenceTypeListRepository

    private lateinit var command: EvidenceTypeListAddEvidenceTypesCommand

    init {
        DataTableType(::evidenceTypeListAddEvidenceTypesParams)
        DataTableType(::evidenceTypeListAssertParams)

        When("I add evidence types to the evidence type list:") { params: EvidenceTypeListAddEvidenceTypesParams ->
            step {
                addEvidenceTypes(params)
            }
        }

        Given("Some evidence types are added to the evidence type list:") { params: EvidenceTypeListAddEvidenceTypesParams ->
            step {
                addEvidenceTypes(params)
            }
        }

        Then("The evidence type list should contain the evidence types") {
            step {
                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThatId(command.id)
                    .specifiesEvidenceTypes(command.evidenceTypeIds)
            }
        }

        Then("The evidence type list should contain the evidence types:") { params: EvidenceTypeListAssertParams ->
            step {
                val evidenceTypeListId = context.evidenceTypeListIds.safeGet(params.identifier)
                val evidenceTypeIds = params.evidenceTypes.map(context.evidenceTypeIds::safeGet)
                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThatId(evidenceTypeListId)
                    .specifiesEvidenceTypes(evidenceTypeIds)
            }
        }
    }

    private suspend fun addEvidenceTypes(params: EvidenceTypeListAddEvidenceTypesParams) {
        command = EvidenceTypeListAddEvidenceTypesCommand(
            id = context.evidenceTypeListIds[params.identifier] ?: params.identifier,
            evidenceTypeIds = params.evidenceTypes.map { context.evidenceTypeIds[it] ?: it }
        )
        evidenceTypeAggregateService.addEvidenceTypes(command)
    }

    private fun evidenceTypeListAddEvidenceTypesParams(entry: Map<String, String>) = EvidenceTypeListAddEvidenceTypesParams(
        identifier = entry["identifier"] ?: context.evidenceTypeListIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class EvidenceTypeListAddEvidenceTypesParams(
        val identifier: TestContextKey,
        val evidenceTypes: List<TestContextKey>
    )

    private fun evidenceTypeListAssertParams(entry: Map<String, String>) = EvidenceTypeListAssertParams(
        identifier = entry["identifier"] ?: context.evidenceTypeListIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class EvidenceTypeListAssertParams(
        val identifier: TestContextKey,
        val evidenceTypes: List<TestContextKey>
    )
}

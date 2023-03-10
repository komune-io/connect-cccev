package cccev.test.s2.evidenceTypeList.command

import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.api.EvidenceTypeAggregateService
import cccev.s2.evidence.domain.command.list.EvidenceTypeListRemoveEvidenceTypesCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.evidenceTypeList.data.evidenceTypeList
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.safeExtractList
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class EvidenceTypeListRemoveEvidenceTypesSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var evidenceTypeAggregateService: EvidenceTypeAggregateService

    @Autowired
    private lateinit var evidenceTypeListRepository: EvidenceTypeListRepository

    private lateinit var command: EvidenceTypeListRemoveEvidenceTypesCommand

    init {
        DataTableType(::evidenceTypeListRemoveEvidenceTypesParams)
        DataTableType(::evidenceTypeListAssertParams)

        When("I remove evidence types from the evidence type list:") { params: EvidenceTypeListRemoveEvidenceTypesParams ->
            step {
                removeEvidenceTypes(params)
            }
        }

        Given("Some evidence types are remove from the evidence type list:") { params: EvidenceTypeListRemoveEvidenceTypesParams ->
            step {
                removeEvidenceTypes(params)
            }
        }

        Then("The evidence type list should not contain the evidence types") {
            step {
                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThatId(command.id)
                    .doesNotSpecifiesEvidenceTypes(command.evidenceTypeIds)
            }
        }

        Then("The evidence type list should not contain the evidence types:") { params: EvidenceTypeListAssertParams ->
            step {
                val evidenceTypeListId = context.evidenceTypeListIds.safeGet(params.identifier)
                val evidenceTypeIds = params.evidenceTypes.map(context.evidenceTypeIds::safeGet)
                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThatId(evidenceTypeListId)
                    .doesNotSpecifiesEvidenceTypes(evidenceTypeIds)
            }
        }
    }

    private suspend fun removeEvidenceTypes(params: EvidenceTypeListRemoveEvidenceTypesParams) {
        command = EvidenceTypeListRemoveEvidenceTypesCommand(
            id = context.evidenceTypeListIds[params.identifier] ?: params.identifier,
            evidenceTypeIds = params.evidenceTypes.map { context.evidenceTypeIds[it] ?: it }
        )
        evidenceTypeAggregateService.removeEvidenceTypes(command)
    }

    private fun evidenceTypeListRemoveEvidenceTypesParams(entry: Map<String, String>) = EvidenceTypeListRemoveEvidenceTypesParams(
        identifier = entry["identifier"] ?: context.evidenceTypeListIds.lastUsedKey,
        evidenceTypes = entry.safeExtractList("evidenceTypes"),
    )

    private data class EvidenceTypeListRemoveEvidenceTypesParams(
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

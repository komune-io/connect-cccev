package cccev.test.s2.evidenceTypeList.command

import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.api.EvidenceTypeAggregateService
import cccev.s2.evidence.domain.EvidenceTypeListState
import cccev.s2.evidence.domain.command.list.EvidenceTypeListCreateCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.evidenceTypeList.data.evidenceTypeList
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.extractList
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class EvidenceTypeListCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var evidenceTypeListAggregateService: EvidenceTypeAggregateService

    @Autowired
    private lateinit var evidenceTypeListRepository: EvidenceTypeListRepository

    private lateinit var command: EvidenceTypeListCreateCommand

    init {
        DataTableType(::evidenceTypeListCreateParams)
        DataTableType(::evidenceTypeListAssertParams)

        When("I create an evidence type list") {
            step {
                createEvidenceTypeList(evidenceTypeListCreateParams(null))
            }
        }

        When("I create an evidence type list:") { params: EvidenceTypeListCreateParams ->
            step {
                createEvidenceTypeList(params)
            }
        }

        Given("An evidence type list is created") {
            step {
                createEvidenceTypeList(evidenceTypeListCreateParams(null))
            }
        }

        Given("An evidence type list is created:") { params: EvidenceTypeListCreateParams ->
            step {
                createEvidenceTypeList(params)
            }
        }

        Given("Some evidence type lists are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(EvidenceTypeListCreateParams::class.java)
                    .forEach { createEvidenceTypeList(it) }
            }
        }

        Then("The evidence type list should be created") {
            step {
                val evidenceTypeListId = context.evidenceTypeListIds.lastUsed
                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThatId(evidenceTypeListId).hasFields(
                    status = EvidenceTypeListState.EXISTS,
                    name = command.name,
                    description = command.description,
                    specifiesEvidenceType = command.specifiesEvidenceType,
                )
            }
        }

        Then("The evidence type list should be created:") { params: EvidenceTypeListAssertParams ->
            step {
                val evidenceTypeListId = context.evidenceTypeListIds.safeGet(params.identifier)
                val evidenceTypeList = evidenceTypeListRepository.findById(evidenceTypeListId).awaitSingleOrNull()
                Assertions.assertThat(evidenceTypeList).isNotNull

                AssertionBdd.evidenceTypeList(evidenceTypeListRepository).assertThat(evidenceTypeList!!).hasFields(
                    status = EvidenceTypeListState.EXISTS,
                    name = params.name ?: evidenceTypeList.name,
                    description = params.description ?: evidenceTypeList.description,
                    specifiesEvidenceType = params.specifiesEvidenceType?.map { context.evidenceTypeIds.safeGet(it) }
                        ?: evidenceTypeList.specifiesEvidenceType.map { it.id }
                )
            }
        }
    }

    private suspend fun createEvidenceTypeList(params: EvidenceTypeListCreateParams) =
        context.evidenceTypeListIds.register(params.identifier) {
        command = EvidenceTypeListCreateCommand(
            name = params.name,
            description = params.description,
            specifiesEvidenceType = params.specifiesEvidenceType.map { context.evidenceTypeIds[it] ?: it }
        )
        evidenceTypeListAggregateService.createList(command).id
    }

    private fun evidenceTypeListCreateParams(entry: Map<String, String>?) = EvidenceTypeListCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        description = entry?.get("description").orRandom(),
        specifiesEvidenceType = entry?.extractList("specifiesEvidenceType").orEmpty(),
    )

    private data class EvidenceTypeListCreateParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String,
        val specifiesEvidenceType: List<TestContextKey>,
    )

    private fun evidenceTypeListAssertParams(entry: Map<String, String>) = EvidenceTypeListAssertParams(
        identifier = entry["identifier"] ?: context.evidenceTypeListIds.lastUsedKey,
        name = entry["name"],
        description = entry["description"],
        specifiesEvidenceType = entry.extractList("specifiesEvidenceType"),
    )

    private data class EvidenceTypeListAssertParams(
        val identifier: TestContextKey,
        val name: String?,
        val description: String?,
        val specifiesEvidenceType: List<TestContextKey>?,
    )
}

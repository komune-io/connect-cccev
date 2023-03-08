package cccev.test.s2.evidenceType.command

import cccev.s2.evidence.api.EvidenceTypeAggregateService
import cccev.s2.evidence.api.entity.type.EvidenceTypeRepository
import cccev.s2.evidence.domain.EvidenceTypeState
import cccev.s2.evidence.domain.command.type.EvidenceTypeCreateCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.evidenceType.data.evidenceType
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class EvidenceTypeCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var evidenceTypeAggregateService: EvidenceTypeAggregateService

    @Autowired
    private lateinit var evidenceTypeRepository: EvidenceTypeRepository

    private lateinit var command: EvidenceTypeCreateCommand

    init {
        DataTableType(::evidenceTypeCreateParams)
        DataTableType(::evidenceTypeAssertParams)

        When("I create an evidence type") {
            step {
                createEvidenceType(evidenceTypeCreateParams(null))
            }
        }

        When("I create an evidence type:") { params: EvidenceTypeCreateParams ->
            step {
                createEvidenceType(params)
            }
        }

        Given("An evidence type is created") {
            step {
                createEvidenceType(evidenceTypeCreateParams(null))
            }
        }

        Given("An evidence type is created:") { params: EvidenceTypeCreateParams ->
            step {
                createEvidenceType(params)
            }
        }

        Given("Some evidence types are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(EvidenceTypeCreateParams::class.java)
                    .forEach { createEvidenceType(it) }
            }
        }

        Then("The evidence type should be created") {
            step {
                val evidenceTypeId = context.evidenceTypeIds.lastUsed
                AssertionBdd.evidenceType(evidenceTypeRepository).assertThatId(evidenceTypeId).hasFields(
                    status = EvidenceTypeState.EXISTS,
                    name = command.name,
                    description = command.description,
                    validityPeriodConstraint = command.validityPeriodConstraint,
                )
            }
        }

        Then("The evidence type should be created:") { params: EvidenceTypeAssertParams ->
            step {
                val evidenceTypeId = context.evidenceTypeIds.safeGet(params.identifier)
                val evidenceType = evidenceTypeRepository.findById(evidenceTypeId).awaitSingleOrNull()
                Assertions.assertThat(evidenceType).isNotNull

                AssertionBdd.evidenceType(evidenceTypeRepository).assertThat(evidenceType!!).hasFields(
                    status = EvidenceTypeState.EXISTS,
                    name = params.name ?: evidenceType.name,
                    description = params.description ?: evidenceType.description,
                    validityPeriodConstraint = params.validityPeriodConstraint
                        .parseNullableOrDefault(evidenceType.validityPeriodConstraint, String::toLong),
                )
            }
        }
    }

    private suspend fun createEvidenceType(params: EvidenceTypeCreateParams) = context.evidenceTypeIds.register(params.identifier) {
        command = EvidenceTypeCreateCommand(
            name = params.name,
            description = params.description,
            validityPeriodConstraint = params.validityPeriodConstraint,
        )
        evidenceTypeAggregateService.create(command).id
    }

    private fun evidenceTypeCreateParams(entry: Map<String, String>?) = EvidenceTypeCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        description = entry?.get("description").orRandom(),
        validityPeriodConstraint = entry?.get("validityPeriodConstraint")?.toLong(),
    )

    private data class EvidenceTypeCreateParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String,
        val validityPeriodConstraint: Long?,
    )

    private fun evidenceTypeAssertParams(entry: Map<String, String>) = EvidenceTypeAssertParams(
        identifier = entry["identifier"] ?: context.evidenceTypeIds.lastUsedKey,
        name = entry["name"],
        description = entry["description"],
        validityPeriodConstraint = entry["validityPeriodConstraint"],
    )

    private data class EvidenceTypeAssertParams(
        val identifier: TestContextKey,
        val name: String?,
        val description: String?,
        val validityPeriodConstraint: String?,
    )
}

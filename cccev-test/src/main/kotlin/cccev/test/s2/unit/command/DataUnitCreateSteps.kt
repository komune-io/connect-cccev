package cccev.test.s2.unit.command

import cccev.s2.unit.api.DataUnitAggregateService
import cccev.s2.unit.api.entity.DataUnitRepository
import cccev.s2.unit.domain.DataUnitState
import cccev.s2.unit.domain.command.DataUnitCreateCommand
import cccev.s2.unit.domain.model.DataUnitType
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.unit.data.dataUnit
import cccev.test.s2.unit.data.extractDataUnitType
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class DataUnitCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var dataUnitAggregateService: DataUnitAggregateService

    @Autowired
    private lateinit var dataUnitRepository: DataUnitRepository

    private lateinit var command: DataUnitCreateCommand

    init {
        DataTableType(::dataUnitCreateParams)
        DataTableType(::dataUnitAssertParams)

        When("I create a data unit") {
            step {
                createDataUnit(dataUnitCreateParams(null))
            }
        }

        When("I create a data unit:") { params: DataUnitCreateParams ->
            step {
                createDataUnit(params)
            }
        }

        Given("A data unit is created") {
            step {
                createDataUnit(dataUnitCreateParams(null))
            }
        }

        Given("A data unit is created:") { params: DataUnitCreateParams ->
            step {
                createDataUnit(params)
            }
        }

        Given("Some data units are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(DataUnitCreateParams::class.java)
                    .forEach { createDataUnit(it) }
            }
        }

        Then("The data unit should be created") {
            step {
                val unitId = context.unitIds.lastUsed
                AssertionBdd.dataUnit(dataUnitRepository).assertThatId(unitId).hasFields(
                    status = DataUnitState.EXISTS,
                    name = command.name,
                    description = command.description,
                    notation = command.notation,
                    type = command.type,
                )
            }
        }

        Then("The data unit should be created:") { params: DataUnitAssertParams ->
            step {
                val unitId = context.unitIds.safeGet(params.identifier)
                val unit = dataUnitRepository.findById(unitId).awaitSingleOrNull()
                Assertions.assertThat(unit).isNotNull

                AssertionBdd.dataUnit(dataUnitRepository).assertThat(unit!!).hasFields(
                    status = DataUnitState.EXISTS,
                    name = params.name ?: unit.name,
                    description = params.description ?: unit.description,
                    notation = params.notation.parseNullableOrDefault(unit.notation),
                    type = params.type ?: unit.type,
                )
            }
        }
    }

    private suspend fun createDataUnit(params: DataUnitCreateParams) = context.unitIds.register(params.identifier) {
        command = DataUnitCreateCommand(
            name = params.name,
            description = params.description,
            notation = params.notation,
            type = params.type
        )
        dataUnitAggregateService.create(command).id
    }

    private fun dataUnitCreateParams(entry: Map<String, String>?) = DataUnitCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        description = entry?.get("description").orRandom(),
        notation = entry?.get("notation"),
        type = entry?.extractDataUnitType("type") ?: DataUnitType.NUMBER
    )

    private data class DataUnitCreateParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String,
        val notation: String?,
        val type: DataUnitType
    )

    private fun dataUnitAssertParams(entry: Map<String, String>) = DataUnitAssertParams(
        identifier = entry["identifier"] ?: context.unitIds.lastUsedKey,
        name = entry["name"],
        description = entry["description"],
        notation = entry["notation"],
        type = entry.extractDataUnitType("type")
    )

    private data class DataUnitAssertParams(
        val identifier: TestContextKey,
        val name: String?,
        val description: String?,
        val notation: String?,
        val type: DataUnitType?
    )
}

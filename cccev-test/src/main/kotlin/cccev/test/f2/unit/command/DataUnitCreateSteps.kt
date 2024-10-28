package cccev.test.f2.unit.command

import cccev.core.unit.entity.DataUnitRepository
import cccev.dsl.model.DataUnitType
import cccev.f2.unit.DataUnitEndpoint
import cccev.f2.unit.command.DataUnitCreateCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.f2.unit.data.dataUnit
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extract

class DataUnitCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var dataUnitEndpoint: DataUnitEndpoint

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
                val unit = dataUnitRepository.findById(unitId)
                Assertions.assertThat(unit).isNotNull

                AssertionBdd.dataUnit(dataUnitRepository).assertThat(unit!!).hasFields(
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
            identifier = params.identifier,
            description = params.description,
            notation = params.notation,
            type = params.type,
            options = emptyList()
        )
        command.invokeWith(dataUnitEndpoint.dataUnitCreate()).id
    }

    private fun dataUnitCreateParams(entry: Map<String, String>?) = DataUnitCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        description = entry?.get("description").orRandom(),
        notation = entry?.get("notation"),
        type = entry?.extract("type") ?: DataUnitType.NUMBER
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
        type = entry.extract("type")
    )

    private data class DataUnitAssertParams(
        val identifier: TestContextKey,
        val name: String?,
        val description: String?,
        val notation: String?,
        val type: DataUnitType?
    )
}

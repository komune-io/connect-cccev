package cccev.test.s2.concept.command

import cccev.s2.concept.api.InformationConceptAggregateService
import cccev.s2.concept.api.entity.InformationConceptRepository
import cccev.s2.concept.domain.InformationConceptState
import cccev.s2.concept.domain.command.InformationConceptCreateCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.concept.data.informationConcept
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.extractList
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class InformationConceptCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var informationConceptAggregateService: InformationConceptAggregateService

    @Autowired
    private lateinit var informationConceptRepository: InformationConceptRepository

    private lateinit var command: InformationConceptCreateCommand

    init {
        DataTableType(::informationConceptCreateParams)
        DataTableType(::informationConceptAssertParams)

        When("I create an information concept") {
            step {
                createInformationConcept(informationConceptCreateParams(null))
            }
        }

        When("I create an information concept:") { params: InformationConceptCreateParams ->
            step {
                createInformationConcept(params)
            }
        }

        Given("An information concept is created") {
            step {
                createInformationConcept(informationConceptCreateParams(null))
            }
        }

        Given("An information concept is created:") { params: InformationConceptCreateParams ->
            step {
                createInformationConcept(params)
            }
        }

        Given("Some information concepts are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(InformationConceptCreateParams::class.java)
                    .forEach { createInformationConcept(it) }
            }
        }

        Then("The information concept should be created") {
            step {
                val conceptId = context.conceptIds.lastUsed
                AssertionBdd.informationConcept(informationConceptRepository).assertThatId(conceptId).hasFields(
                    status = InformationConceptState.EXISTS,
                    name = command.name,
                    unitId = command.unitId,
                    description = command.description,
                    expressionOfExpectedValue = command.expressionOfExpectedValue,
                    dependsOn = command.dependsOn,
                )
            }
        }

        Then("The information concept should be created:") { params: InformationConceptAssertParams ->
            step {
                val conceptId = context.conceptIds.safeGet(params.identifier)
                val concept = informationConceptRepository.findById(conceptId).awaitSingleOrNull()
                Assertions.assertThat(concept).isNotNull

                AssertionBdd.informationConcept(informationConceptRepository).assertThat(concept!!).hasFields(
                    status = InformationConceptState.EXISTS,
                    name = params.name ?: concept.name,
                    unitId = params.unit?.let(context.unitIds::safeGet) ?: concept.unitId,
                    description = params.description ?: concept.description,
                    expressionOfExpectedValue = params.expressionOfExpectedValue.parseNullableOrDefault(concept.expressionOfExpectedValue),
                    dependsOn = params.dependsOn ?: concept.dependsOn,
                )
            }
        }
    }

    private suspend fun createInformationConcept(params: InformationConceptCreateParams) = context.conceptIds.register(params.identifier) {
        command = InformationConceptCreateCommand(
            name = params.name,
            unitId = context.unitIds[params.unit] ?: params.unit,
            description = params.description,
            expressionOfExpectedValue = params.expressionOfExpectedValue,
            dependsOn = params.dependsOn
        )
        informationConceptAggregateService.create(command).id
    }

    private fun informationConceptCreateParams(entry: Map<String, String>?) = InformationConceptCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        unit = entry?.get("unit") ?: context.unitIds.lastUsedKey,
        description = entry?.get("description").orRandom(),
        expressionOfExpectedValue = entry?.get("expressionOfExpectedValue"),
        dependsOn = entry?.extractList("dependsOn").orEmpty()
    )

    private data class InformationConceptCreateParams(
        val identifier: TestContextKey,
        val name: String,
        val unit: TestContextKey,
        val description: String,
        val expressionOfExpectedValue: String?,
        val dependsOn: List<TestContextKey>
    )

    private fun informationConceptAssertParams(entry: Map<String, String>) = InformationConceptAssertParams(
        identifier = entry["identifier"] ?: context.conceptIds.lastUsedKey,
        name = entry["name"],
        unit = entry["unit"],
        description = entry["description"],
        expressionOfExpectedValue = entry["expressionOfExpectedValue"],
        dependsOn = entry.extractList("dependsOn")
    )

    private data class InformationConceptAssertParams(
        val identifier: TestContextKey,
        val name: String?,
        val unit: TestContextKey?,
        val description: String?,
        val expressionOfExpectedValue: String?,
        val dependsOn: List<TestContextKey>?
    )
}

package cccev.test.f2.concept.command

import cccev.client.InformationConceptClient
import cccev.client.informationConceptClient
import cccev.core.concept.entity.InformationConceptRepository
import cccev.f2.concept.InformationConceptEndpoint
import cccev.f2.concept.command.InformationConceptCreateCommand
import cccev.f2.concept.query.InformationConceptGetByIdentifierQuery
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.f2.concept.data.informationConcept
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extractList

class InformationConceptCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var informationConceptEndpoint: InformationConceptEndpoint


    @Value("\${server.port}")
    private lateinit var randomServerPort: String

    fun informationConceptClient(): InformationConceptClient = runBlocking {
        informationConceptClient("http://localhost:${randomServerPort}").invoke()
    }


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

                val concept = informationConceptRepository.findById(conceptId)
                Assertions.assertThat(concept).isNotNull

                val conceptByIdentifier = informationConceptRepository.findByIdentifier(concept!!.identifier)
                Assertions.assertThat(conceptByIdentifier).isNotNull
                val conceptByIdentifier2 = InformationConceptGetByIdentifierQuery(concept.identifier).invokeWith(informationConceptEndpoint.conceptGetByIdentifier()).item
                Assertions.assertThat(conceptByIdentifier2).isNotNull

                AssertionBdd.informationConcept(informationConceptRepository).assertThatId(conceptId).hasFields(
                    name = command.name,
                    unit = command.hasUnit,
                    description = command.description,
                    expressionOfExpectedValue = command.expressionOfExpectedValue,
                    dependencies = command.dependsOn,
                )
            }
        }

        Then("The information concept should be created:") { params: InformationConceptAssertParams ->
            step {
                val conceptId = context.conceptIds.safeGet(params.identifier)
                val concept = informationConceptRepository.findById(conceptId)
                Assertions.assertThat(concept).isNotNull


                val conceptByIdentifier = InformationConceptGetByIdentifierQuery(params.identifier).invokeWith(informationConceptEndpoint.conceptGetByIdentifier()).item
                Assertions.assertThat(conceptByIdentifier).isNotNull

                AssertionBdd.informationConcept(informationConceptRepository).assertThat(concept!!).hasFields(
                    name = params.name ?: concept.name,
                    unit = params.unit?.let(context.unitIds::safeGet) ?: concept.unit.id,
                    description = params.description ?: concept.description,
                    expressionOfExpectedValue = params.expressionOfExpectedValue.parseNullableOrDefault(concept.expressionOfExpectedValue),
                    dependencies = params.dependsOn ?: concept.dependencies.map { it.id },
                )
            }
        }
    }

    private suspend fun createInformationConcept(params: InformationConceptCreateParams) = context.conceptIds.register(params.identifier) {
        command = InformationConceptCreateCommand(
            identifier = params.identifier,
            name = params.name,
            hasUnit = context.unitIds[params.unit] ?: params.unit,
            description = params.description,
            expressionOfExpectedValue = params.expressionOfExpectedValue,
            dependsOn = params.dependsOn?.map(context.conceptIds::safeGet)
        )
        command.invokeWith(informationConceptEndpoint.conceptCreate()).id
    }

    private fun informationConceptCreateParams(entry: Map<String, String>?) = InformationConceptCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        name = entry?.get("name").orRandom(),
        unit = entry?.get("unit") ?: context.unitIds.lastUsedKey,
        description = entry?.get("description").orRandom(),
        expressionOfExpectedValue = entry?.get("expressionOfExpectedValue"),
        dependsOn = entry?.extractList("dependsOn")
    )

    private data class InformationConceptCreateParams(
        val identifier: TestContextKey,
        val name: String,
        val unit: TestContextKey,
        val description: String,
        val expressionOfExpectedValue: String?,
        val dependsOn: List<TestContextKey>?
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

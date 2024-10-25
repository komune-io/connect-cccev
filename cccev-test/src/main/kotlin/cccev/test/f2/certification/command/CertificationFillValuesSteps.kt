package cccev.test.f2.certification.command

import cccev.core.certification.command.CertificationFillValuesCommand
import cccev.f2.certification.CertificationEndpoint
import cccev.test.CccevCucumberStepsDefinition
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class CertificationFillValuesSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var certificationEndpoint: CertificationEndpoint

    private lateinit var command: CertificationFillValuesCommand

    init {
        DataTableType(::certificationFillValuesParams)

        When("I fill values in the certification:") { dataTable: DataTable ->
            step {
                dataTable.asList(CertificationFillValuesParams::class.java)
                    .groupBy(CertificationFillValuesParams::identifier)
                    .forEach { (identifier, params) ->
                        fillValues(identifier, params.associate { it.concept to it.value })
                    }
            }
        }

        When("Some values are filled in the certification:") { dataTable: DataTable ->
            step {
                dataTable.asList(CertificationFillValuesParams::class.java)
                    .groupBy(CertificationFillValuesParams::identifier)
                    .forEach { (identifier, params) ->
                        fillValues(identifier, params.associate { it.concept to it.value })
                    }
            }
        }
    }

    private suspend fun fillValues(identifier: TestContextKey, values: Map<TestContextKey, String?>) {
        command = CertificationFillValuesCommand(
            id = context.certificationIds[identifier] ?: identifier,
            rootRequirementCertificationId = null,
            values = values
        )
        command.invokeWith(certificationEndpoint.certificationFillValues())
    }

    private fun certificationFillValuesParams(entry: Map<String, String>) = CertificationFillValuesParams(
        identifier = entry["identifier"] ?: context.certificationIds.lastUsedKey,
        concept = entry.safeExtract("concept"),
        value = entry["value"]
    )

    private data class CertificationFillValuesParams(
        val identifier: TestContextKey,
        val concept: TestContextKey,
        val value: String?
    )
}

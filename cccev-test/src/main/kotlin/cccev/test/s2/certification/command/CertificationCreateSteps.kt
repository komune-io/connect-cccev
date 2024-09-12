package cccev.test.s2.certification.command

import au.com.origin.snapshots.Expect
import cccev.core.certification.CertificationAggregateService
import cccev.core.certification.command.CertificationCreateCommand
import cccev.core.certification.entity.CertificationRepository
import cccev.test.CccevCucumberStepsDefinition
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extractList
import kotlin.reflect.jvm.javaMethod

class CertificationCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var certificationAggregateService: CertificationAggregateService

    @Autowired
    private lateinit var certificationRepository: CertificationRepository

    private lateinit var command: CertificationCreateCommand

    init {
        DataTableType(::certificationCreateParams)

        When("I create a certification") {
            step {
                createCertification(certificationCreateParams(null))
            }
        }

        When("I create a certification:") { params: CertificationCreateParams ->
            step {
                createCertification(params)
            }
        }

        Given("A certification is created") {
            step {
                createCertification(certificationCreateParams(null))
            }
        }

        Given("A certification is created:") { params: CertificationCreateParams ->
            step {
                createCertification(params)
            }
        }

        Given("Some certifications are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(CertificationCreateParams::class.java)
                    .forEach { createCertification(it) }
            }
        }

        Then("The certification should match the snapshot {string}") { snapshot: String ->
            step {
                val start = System.currentTimeMillis()
                val certification = certificationRepository.findById(context.certificationIds.lastUsed)
                println("Certification fetch took ${System.currentTimeMillis() - start}ms")

                Expect.of(context.snapshotVerifier, ::snapshot.javaMethod)
                    .scenario(snapshot)
                    .toMatchSnapshot(certification)
            }
        }
    }

    private fun snapshot() { /* do nothing */ }

    private suspend fun createCertification(params: CertificationCreateParams) = context.certificationIds.register(params.identifier) {
        command = CertificationCreateCommand(
            id = params.identifier,
            requirementIdentifiers = params.requirements
        )
        certificationAggregateService.create(command).id
    }

    private fun certificationCreateParams(entry: Map<String, String>?) = CertificationCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        requirements = entry?.extractList("requirements") ?: emptyList()
    )

    private data class CertificationCreateParams(
        val identifier: TestContextKey,
        val requirements: List<TestContextKey>
    )
}

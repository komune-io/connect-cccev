package cccev.test.f2.certification.command

import cccev.core.certification.command.CertificationAddRequirementsCommand
import cccev.core.certification.entity.CertificationRepository
import cccev.f2.CccevFlatGraph
import cccev.f2.certification.CertificationEndpoint
import cccev.f2.certification.model.flattenTo
import cccev.test.CccevCucumberStepsDefinition
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtractList

class CertificationAddRequirementsSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var certificationEndpoint: CertificationEndpoint

    @Autowired
    private lateinit var certificationRepository: CertificationRepository

    private lateinit var command: CertificationAddRequirementsCommand

    init {
        DataTableType(::certificationAddRequirementsParams)

        When("I add requirements to the certification:") { params: CertificationAddRequirementsParams ->
            step {
                addRequirements(params)
            }
        }

        Given("Some requirements are added to the certification:") { params: CertificationAddRequirementsParams ->
            step {
                addRequirements(params)
            }
        }
    }

    private suspend fun addRequirements(params: CertificationAddRequirementsParams) {
        command = CertificationAddRequirementsCommand(
            id = context.certificationIds[params.identifier] ?: params.identifier,
            parentId = null,
            requirementIdentifiers = params.requirements,
        )
        command.invokeWith(certificationEndpoint.certificationAddRequirements())

        val graph = CccevFlatGraph()
        certificationRepository.findById(command.id)!!.flattenTo(graph)

        graph.requirementCertifications.forEach { (_, requirementCertification) ->
            val key = params.identifier to requirementCertification.requirementIdentifier
            context.requirementCertificationIds[key] = requirementCertification.id
        }
    }

    private fun certificationAddRequirementsParams(entry: Map<String, String>) = CertificationAddRequirementsParams(
        identifier = entry["identifier"] ?: context.certificationIds.lastUsedKey,
        requirements = entry.safeExtractList("requirements")
    )

    private data class CertificationAddRequirementsParams(
        val identifier: TestContextKey,
        val requirements: List<TestContextKey>
    )
}

package cccev.test.f2.certification.command

import cccev.commons.utils.mapAsync
import cccev.core.certification.command.CertificationCreateCommand
import cccev.core.certification.entity.CertificationRepository
import cccev.f2.CccevFlatGraph
import cccev.f2.certification.CertificationEndpoint
import cccev.f2.certification.model.flattenTo
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.CertificationKey
import cccev.test.RequirementKey
import cccev.test.SupportedValueKey
import cccev.test.f2.certification.data.requirementCertification
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.parser.extractList
import s2.bdd.data.parser.safeExtract

class CertificationCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var certificationEndpoint: CertificationEndpoint

    @Autowired
    private lateinit var certificationRepository: CertificationRepository

    private lateinit var command: CertificationCreateCommand

    init {
        DataTableType(::certificationCreateParams)
        DataTableType(::requirementCertificationAssertParams)

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

        Then("The requirement certifications should be created:") { dataTable: DataTable ->
            step {
                dataTable.asList(RequirementCertificationAssertParams::class.java)
                    .mapAsync(::assertRequirementCertification)
            }
        }
    }

    private suspend fun createCertification(params: CertificationCreateParams) {
        command = CertificationCreateCommand(
            id = params.identifier,
            requirementIdentifiers = params.requirements
        )
        val certificationId = command.invokeWith(certificationEndpoint.certificationCreate()).id
        context.certificationIds[params.identifier] = certificationId

        val graph = CccevFlatGraph()
        certificationRepository.findById(certificationId)!!.flattenTo(graph)

        graph.requirementCertifications.forEach { (_, requirementCertification) ->
            val key = params.identifier to requirementCertification.requirementIdentifier
            context.requirementCertificationIds[key] = requirementCertification.id
        }
    }

    private suspend fun assertRequirementCertification(params: RequirementCertificationAssertParams) {
        val requirementCertificationId = context.requirementCertificationIds.safeGet(params.certification to params.requirement)
        val requirementCertification = certificationRepository.findRequirementCertificationById(requirementCertificationId)

        Assertions.assertThat(requirementCertification).isNotNull()
        AssertionBdd.requirementCertification(certificationRepository).assertThat(requirementCertification!!)
            .hasFields(
                subCertificationIds = params.subCertifications?.map {
                    context.requirementCertificationIds.safeGet(params.certification to it)
                } ?: requirementCertification.subCertifications.map { it.id },
                values = params.values?.map { context.supportedValueIds.safeGet(it) }
                    ?: requirementCertification.values.map { it.id },
                isEnabled = params.isEnabled ?: requirementCertification.isEnabled,
                isValidated = params.isValidated ?: requirementCertification.isValidated,
                hasAllValues = params.hasAllValues ?: requirementCertification.hasAllValues,
                isFulfilled = params.isFulfilled ?: requirementCertification.isFulfilled
            )
    }

    private fun certificationCreateParams(entry: Map<String, String>?) = CertificationCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        requirements = entry?.extractList<RequirementKey>("requirements").orEmpty()
    )

    private fun requirementCertificationAssertParams(entry: Map<String, String>) = RequirementCertificationAssertParams(
        certification = entry["certification"] ?: context.certificationIds.lastUsedKey,
        requirement = entry.safeExtract("requirement"),
        subCertifications = entry.extractList<RequirementKey>("subCertifications").orEmpty().takeIf { "subCertifications" in entry },
        values = entry.extractList<RequirementKey>("values").orEmpty().takeIf { "values" in entry },
        isEnabled = entry["isEnabled"]?.toBoolean(),
        isValidated = entry["isValidated"]?.toBoolean(),
        hasAllValues = entry["hasAllValues"]?.toBoolean(),
        isFulfilled = entry["isFulfilled"]?.toBoolean()
    )

    private data class CertificationCreateParams(
        val identifier: CertificationKey,
        val requirements: List<RequirementKey>
    )

    private data class RequirementCertificationAssertParams(
        val certification: CertificationKey,
        val requirement: RequirementKey,
        val subCertifications: List<RequirementKey>?,
        val values: List<SupportedValueKey>?,
        val isEnabled: Boolean?,
        val isValidated: Boolean?,
        val hasAllValues: Boolean?,
        val isFulfilled: Boolean?
    )
}

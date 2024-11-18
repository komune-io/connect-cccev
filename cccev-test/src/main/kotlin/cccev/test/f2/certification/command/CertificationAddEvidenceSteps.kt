package cccev.test.f2.certification.command

import cccev.core.certification.entity.CertificationRepository
import cccev.f2.certification.CertificationEndpoint
import cccev.f2.certification.command.CertificationAddEvidenceCommand
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.CertificationKey
import cccev.test.EvidenceKey
import cccev.test.EvidenceTypeKey
import cccev.test.f2.certification.data.evidence
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.fs.s2.file.client.FileClient
import io.komune.fs.s2.file.domain.model.FilePath
import java.util.UUID
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.StubFilePart

class CertificationAddEvidenceSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var certificationEndpoint: CertificationEndpoint

    @Autowired
    private lateinit var certificationRepository: CertificationRepository

    @Autowired
    private lateinit var fileClient: FileClient

    private lateinit var command: CertificationAddEvidenceCommand

    init {
        DataTableType(::certificationAddEvidenceParams)
        DataTableType(::evidenceAssertParams)

        When("I add an evidence to the certification:") { params: CertificationAddEvidenceParams ->
            step {
                addEvidence(params)
            }
        }

        Given("An evidence is added to the certification:") { params: CertificationAddEvidenceParams ->
            step {
                addEvidence(params)
            }
        }

        Given("Some evidences are added to the certifications:") { dataTable: DataTable ->
            step {
                dataTable.asList(CertificationAddEvidenceParams::class.java)
                    .forEach { addEvidence(it) }
            }
        }

        Then("The evidences should be created") {
            step {
                val evidenceId = context.evidenceIds.lastUsed
                val evidence = certificationRepository.findEvidenceById(evidenceId)
                Assertions.assertThat(evidence).isNotNull

                AssertionBdd.evidence(certificationRepository).assertThat(evidence!!)
                    .hasFields(
                        filePath = command.filePath ?: evidence.file,
                        evidenceTypeId = command.evidenceTypeId
                    )

                command.filePath?.let {
                    val file = fileClient.fileGet(listOf(it)).first().item
                    Assertions.assertThat(file).isNotNull
                }
            }
        }

        Then("The evidences should be created:") { dataTable: DataTable ->
            step {
                dataTable.asList(EvidenceAssertParams::class.java)
                    .forEach { params ->
                        val evidenceId = context.evidenceIds.safeGet(params.identifier)
                        val evidence = certificationRepository.findEvidenceById(evidenceId)
                        Assertions.assertThat(evidence).isNotNull

                        AssertionBdd.evidence(certificationRepository).assertThat(evidence!!)
                            .hasFields(
                                filePath = params.path?.let { FilePath.from(it) } ?: evidence.file,
                                evidenceTypeId = params.evidenceType?.let(context.evidenceTypeIds::safeGet) ?: evidence.evidenceType.id
                            )

                        params.path?.let {
                            val file = fileClient.fileGet(listOf(FilePath.from(it))).first().item
                            Assertions.assertThat(file).isNotNull
                        }
                    }
            }
        }
    }

    private suspend fun addEvidence(params: CertificationAddEvidenceParams) = context.evidenceIds.register(params.identifier) {
        command = CertificationAddEvidenceCommand(
            id = context.certificationIds[params.certification] ?: params.certification,
            rootRequirementCertificationId = null,
            evidenceTypeId = context.evidenceTypeIds[params.evidenceType] ?: params.evidenceType,
            filePath = params.path?.let { FilePath.from(it) }
        )
        certificationEndpoint.certificationAddEvidence(
            command = command,
            file = StubFilePart(UUID.randomUUID().toString())
        ).evidenceId
    }

    private fun certificationAddEvidenceParams(entry: Map<String, String>?) = CertificationAddEvidenceParams(
        identifier = entry?.get("identifier").orRandom(),
        certification = entry?.get("certification") ?: context.certificationIds.lastUsedKey,
        evidenceType = entry?.get("evidenceType") ?: context.evidenceTypeIds.lastUsedKey,
        path = entry?.get("path")
    )

    private fun evidenceAssertParams(entry: Map<String, String>) = EvidenceAssertParams(
        identifier = entry["identifier"] ?: context.evidenceIds.lastUsedKey,
        evidenceType = entry["evidenceType"]?.let(context.evidenceTypeIds::safeGet),
        path = entry["path"]
    )

    private data class CertificationAddEvidenceParams(
        val identifier: EvidenceKey,
        val certification: CertificationKey,
        val evidenceType: EvidenceTypeKey,
        val path: String?
    )

    private data class EvidenceAssertParams(
        val identifier: EvidenceKey,
        val evidenceType: EvidenceTypeKey?,
        val path: String?
    )
}

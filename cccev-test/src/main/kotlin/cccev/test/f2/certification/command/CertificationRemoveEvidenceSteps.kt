//package cccev.test.s2.certification.command
//
//import cccev.projection.api.entity.certification.CertificationRepositoryOld
//import cccev.s2.certification.api.CertificationAggregateService
//import cccev.s2.certification.domain.command.CertificationRemoveEvidenceCommand
//import cccev.test.CccevCucumberStepsDefinition
//import cccev.test.s2.certification.data.certification
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import kotlinx.coroutines.reactor.awaitSingleOrNull
//import org.assertj.core.api.Assertions
//import org.springframework.beans.factory.annotation.Autowired
//import s2.bdd.assertion.AssertionBdd
//import s2.bdd.data.TestContextKey
//
//class CertificationRemoveEvidenceSteps: En, CccevCucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var certificationAggregateService: CertificationAggregateService
//
//    @Autowired
//    private lateinit var certificationRepository: CertificationRepositoryOld
//
//    private lateinit var command: CertificationRemoveEvidenceCommand
//
//    init {
//        DataTableType(::certificationRemoveEvidenceParams)
//        DataTableType(::certificationAssertParams)
//
//        When("I remove an evidence from the certification:") { params: CertificationRemoveEvidenceParams ->
//            step {
//                removeEvidence(params)
//            }
//        }
//
//        Given("An evidence is removed from the certification:") { params: CertificationRemoveEvidenceParams ->
//            step {
//                removeEvidence(params)
//            }
//        }
//
//        Given("Some evidences are removed from the certifications:") { dataTable: DataTable ->
//            step {
//                dataTable.asList(CertificationRemoveEvidenceParams::class.java)
//                    .forEach { removeEvidence(it) }
//            }
//        }
//
//        Then("The certification should not contain the evidence") {
//            step {
//                val certificationId = context.certificationIds.lastUsed
//                AssertionBdd.certification(certificationRepository).assertThatId(certificationId)
//                    .doesNotHaveEvidences(listOf(command.evidenceId))
//            }
//        }
//
//        Then("The certification should not contain the evidence:") { params: CertificationAssertParams ->
//            step {
//                val certificationId = context.certificationIds.safeGet(params.identifier)
//                val certification = certificationRepository.findById(certificationId).awaitSingleOrNull()
//                Assertions.assertThat(certification).isNotNull
//
//                AssertionBdd.certification(certificationRepository).assertThatId(certificationId)
//                    .doesNotHaveEvidences(listOf(context.evidenceIds.safeGet(params.evidence)))
//            }
//        }
//    }
//
//    private suspend fun removeEvidence(params: CertificationRemoveEvidenceParams) {
//        command = CertificationRemoveEvidenceCommand(
//            id = context.certificationIds[params.identifier] ?: params.identifier,
//            evidenceId = context.evidenceIds[params.evidence] ?: params.evidence
//        )
//        certificationAggregateService.removeEvidence(command)
//    }
//
//    private fun certificationRemoveEvidenceParams(entry: Map<String, String>) = CertificationRemoveEvidenceParams(
//        identifier = entry["identifier"] ?: context.certificationIds.lastUsedKey,
//        evidence = entry["evidence"] ?: context.evidenceIds.lastUsedKey,
//    )
//
//    private data class CertificationRemoveEvidenceParams(
//        val identifier: TestContextKey,
//        val evidence: TestContextKey,
//    )
//
//    private fun certificationAssertParams(entry: Map<String, String>) = CertificationAssertParams(
//        identifier = entry["identifier"] ?: context.certificationIds.lastUsedKey,
//        evidence = entry["evidence"] ?: context.evidenceIds.lastUsedKey,
//    )
//
//    private data class CertificationAssertParams(
//        val identifier: TestContextKey,
//        val evidence: TestContextKey
//    )
//}

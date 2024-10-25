//package cccev.test.s2.certification.query
//
//import cccev.projection.api.entity.certification.CertificationRepositoryOld
//import cccev.s2.certification.api.CertificationFinderService
//import cccev.test.CccevCucumberStepsDefinition
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import org.assertj.core.api.Assertions
//import org.springframework.beans.factory.annotation.Autowired
//import s2.bdd.data.TestContextKey
//import s2.bdd.data.parser.safeExtract
//
//class CertificationGetSteps: En, CccevCucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var certificationFinderService: CertificationFinderService
//
//    @Autowired
//    private lateinit var certificationRepository: CertificationRepositoryOld
//
//    init {
//        DataTableType(::certificationGetParams)
//        DataTableType(::requirementStatsAssertParams)
//
//        When("I fetch the certification") {
//            step {
//                fetchCertification(certificationGetParams(null))
//            }
//        }
//
//        When("I fetch the certification:") { params: CertificationGetParams ->
//            step {
//                fetchCertification(params)
//            }
//        }
//
//        Then("I should receive the requirement stats of the certification:") { dataTable: DataTable ->
//            val params = dataTable.asList(RequirementStatsAssertParams::class.java)
//            val certification = context.fetched.certification
//            params.forEach { param ->
//                val requirementId = context.requirementIds.safeGet(param.requirement)
//                val stats = certification.requirementStats[requirementId]
//                Assertions.assertThat(stats).isNotNull
//                Assertions.assertThat(stats!!.completion).isEqualTo(param.completion)
//            }
//        }
//    }
//
//    private suspend fun fetchCertification(params: CertificationGetParams) {
//        context.fetched.certification = certificationFinderService.get(context.certificationIds[params.identifier] ?: params.identifier)
//    }
//
//    private fun certificationGetParams(entry: Map<String, String>?) = CertificationGetParams(
//        identifier = entry?.get("identifier") ?: context.certificationIds.lastUsedKey
//    )
//
//    private data class CertificationGetParams(
//        val identifier: TestContextKey
//    )
//
//    private fun requirementStatsAssertParams(entry: Map<String, String>) = RequirementStatsAssertParams(
//        requirement = entry.safeExtract("requirement"),
//        completion = entry.safeExtract("completion").toDouble()
//    )
//
//    private data class RequirementStatsAssertParams(
//        val requirement: TestContextKey,
//        val completion: Double
//    )
//}

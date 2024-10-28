//package cccev.f2.certification.domain.query
//
//import cccev.s2.certification.domain.model.EvidenceId
//import cccev.s2.certification.domain.model.CertificationId
//import kotlin.js.JsExport
//import kotlin.js.JsName
//
///**
// * Download a given evidence of a given certification
// * @d2 function
// */
//interface CertificationDownloadEvidenceFunction
//
///**
// * @d2 query
// * @parent [CertificationDownloadEvidenceFunction]
// */
//@JsExport
//@JsName("CertificationDownloadEvidenceQueryDTO")
//interface CertificationDownloadEvidenceQueryDTO {
//    val id: CertificationId
//    val evidenceId: EvidenceId
//}
//
//data class CertificationDownloadEvidenceQueryDTOBase(
//    override val id: CertificationId,
//    override val evidenceId: EvidenceId
//): CertificationDownloadEvidenceQueryDTO

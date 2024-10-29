package cccev.f2.certification.model

import cccev.dsl.model.CertificationId
import cccev.dsl.model.EvidenceTypeId
import io.komune.fs.s2.file.domain.model.FilePath


object CertificationFsPath {
    const val OBJECT_TYPE = "certification"
    const val EVIDENCE_TYPE = "evidence_type"

    fun pathEvidenceType(certificationId: CertificationId, evidenceTypeId: EvidenceTypeId) = FilePath(
        objectType = OBJECT_TYPE,
        objectId = certificationId,
        directory = "${EVIDENCE_TYPE}_evidenceTypeId",
        name = ""
    )
}

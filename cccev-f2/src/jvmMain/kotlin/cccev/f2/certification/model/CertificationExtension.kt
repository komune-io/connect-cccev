package cccev.f2.certification.model

import cccev.core.certification.entity.Certification
import cccev.core.certification.model.CertificationId
import cccev.f2.CccevFlatGraph

fun Certification.flattenTo(graph: CccevFlatGraph): CertificationId {
    graph.certifications[id] = CertificationFlat(
        id = id,
        requirementCertificationIds = requirementCertifications.map { it.flattenTo(graph) },
    )
    return id
}

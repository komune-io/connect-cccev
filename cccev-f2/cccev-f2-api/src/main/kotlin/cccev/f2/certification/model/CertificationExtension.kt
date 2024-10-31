package cccev.f2.certification.model

import cccev.core.certification.entity.CertificationEntity
import cccev.dsl.model.CertificationId
import cccev.f2.CccevFlatGraph

fun CertificationEntity.flattenTo(graph: CccevFlatGraph): CertificationId {
    graph.certifications[id] = CertificationFlat(
        id = id,
        requirementCertificationIds = requirementCertifications.map { it.flattenTo(graph) },
    )
    return id
}

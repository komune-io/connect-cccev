package cccev.dsl.client.model

import cccev.f2.certification.domain.query.CertificationGetResult
import cccev.f2.commons.CccevFlatGraph

fun CertificationGetResult.toCertificationFlatGraph() = certification?.let {
    CccevFlatGraph().also { graph ->
        graph.certifications[it.id] = it
        graph.requirementCertifications.putAll(requirementCertifications)
        graph.requirements.putAll(requirements)
        graph.concepts.putAll(concepts)
        graph.units.putAll(units)
        graph.unitOptions.putAll(unitOptions)
        graph.supportedValues.putAll(supportedValues)
    }
}

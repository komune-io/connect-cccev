package cccev.f2.certification.domain.model

import cccev.dsl.model.SupportedValueId
import cccev.s2.concept.domain.model.InformationConcept

class SupportedValue(
    val id: SupportedValueId,
    val value: String?,
    val concept: InformationConcept
)
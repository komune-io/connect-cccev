package cccev.f2.commons

import cccev.core.certification.model.RequirementCertificationId
import cccev.core.certification.model.SupportedValueId
import cccev.f2.certification.domain.model.CertificationFlat
import cccev.f2.certification.domain.model.RequirementCertificationFlat
import cccev.f2.certification.domain.model.SupportedValueFlat
import cccev.f2.concept.domain.model.InformationConceptFlat
import cccev.f2.requirement.domain.model.RequirementFlat
import cccev.f2.unit.domain.model.DataUnitFlat
import cccev.f2.unit.domain.model.DataUnitOption
import cccev.s2.concept.domain.InformationConceptIdentifier
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.unit.domain.DataUnitIdentifier
import cccev.s2.unit.domain.DataUnitOptionIdentifier

class CertificationFlatGraph {
    lateinit var certification: CertificationFlat
    val requirementCertifications: MutableMap<RequirementCertificationId, RequirementCertificationFlat> = mutableMapOf()
    val requirements: MutableMap<RequirementIdentifier, RequirementFlat> = mutableMapOf()
    val concepts: MutableMap<InformationConceptIdentifier, InformationConceptFlat> = mutableMapOf()
    val units: MutableMap<DataUnitIdentifier, DataUnitFlat> = mutableMapOf()
    val unitOptions: MutableMap<DataUnitOptionIdentifier, DataUnitOption> = mutableMapOf()
    val supportedValues: MutableMap<SupportedValueId, SupportedValueFlat> = mutableMapOf()
}

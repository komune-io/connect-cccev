package cccev.f2

import cccev.dsl.model.CertificationId
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitOption
import cccev.dsl.model.DataUnitOptionDTO
import cccev.dsl.model.DataUnitOptionIdentifier
import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.EvidenceTypeListId
import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.RequirementCertificationId
import cccev.dsl.model.RequirementIdentifier
import cccev.dsl.model.SupportedValueId
import cccev.f2.certification.model.CertificationFlat
import cccev.f2.certification.model.CertificationFlatDTO
import cccev.f2.certification.model.RequirementCertificationFlat
import cccev.f2.certification.model.RequirementCertificationFlatDTO
import cccev.f2.certification.model.SupportedValueFlat
import cccev.f2.certification.model.SupportedValueFlatDTO
import cccev.f2.concept.model.InformationConceptFlat
import cccev.f2.concept.model.InformationConceptFlatDTO
import cccev.f2.evidencetype.model.EvidenceTypeFlat
import cccev.f2.evidencetype.model.EvidenceTypeFlatDTO
import cccev.f2.evidencetypelist.model.EvidenceTypeListFlat
import cccev.f2.evidencetypelist.model.EvidenceTypeListFlatDTO
import cccev.f2.requirement.model.RequirementFlat
import cccev.f2.requirement.model.RequirementFlatDTO
import cccev.f2.unit.model.DataUnitFlat
import cccev.f2.unit.model.DataUnitFlatDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@JsExport
interface CccevFlatGraphDTO {
    val certifications: Map<CertificationId, CertificationFlatDTO>
    val requirementCertifications: Map<RequirementCertificationId, RequirementCertificationFlatDTO>
    val requirements: Map<RequirementIdentifier, RequirementFlatDTO>
    val concepts: Map<InformationConceptIdentifier, InformationConceptFlatDTO>
    val evidenceListTypes: Map<EvidenceTypeListId, EvidenceTypeListFlatDTO>
    val evidenceTypes: Map<EvidenceTypeId, EvidenceTypeFlatDTO>
    val units: Map<DataUnitIdentifier, DataUnitFlatDTO>
    val unitOptions: Map<DataUnitOptionIdentifier, DataUnitOptionDTO>
    val supportedValues: Map<SupportedValueId, SupportedValueFlatDTO>
}

@Serializable
class CccevFlatGraph: CccevFlatGraphDTO {
    override val certifications: MutableMap<CertificationId, CertificationFlat> = mutableMapOf()
    override val requirementCertifications: MutableMap<RequirementCertificationId, RequirementCertificationFlat> = mutableMapOf()
    override val requirements: MutableMap<RequirementIdentifier, RequirementFlat> = mutableMapOf()
    override val concepts: MutableMap<InformationConceptIdentifier, InformationConceptFlat> = mutableMapOf()
    override val evidenceListTypes: MutableMap<EvidenceTypeListId, EvidenceTypeListFlat> = mutableMapOf()
    override val evidenceTypes: MutableMap<EvidenceTypeId, EvidenceTypeFlat> = mutableMapOf()
    override val units: MutableMap<DataUnitIdentifier, DataUnitFlat> = mutableMapOf()
    override val unitOptions: MutableMap<DataUnitOptionIdentifier, DataUnitOption> = mutableMapOf()
    override val supportedValues: MutableMap<SupportedValueId, SupportedValueFlat> = mutableMapOf()
}

package cccev.f2.certification.domain.query

import cccev.dsl.model.CertificationId
import cccev.dsl.model.RequirementCertificationId
import cccev.dsl.model.SupportedValueId
import cccev.f2.certification.domain.model.CertificationFlat
import cccev.f2.certification.domain.model.CertificationFlatDTO
import cccev.f2.certification.domain.model.RequirementCertificationFlat
import cccev.f2.certification.domain.model.RequirementCertificationFlatDTO
import cccev.f2.certification.domain.model.SupportedValueFlat
import cccev.f2.certification.domain.model.SupportedValueFlatDTO
import cccev.f2.concept.domain.model.InformationConceptFlat
import cccev.f2.concept.domain.model.InformationConceptFlatDTO
import cccev.f2.requirement.domain.model.RequirementFlat
import cccev.f2.requirement.domain.model.RequirementFlatDTO
import cccev.f2.unit.domain.model.DataUnitFlat
import cccev.f2.unit.domain.model.DataUnitFlatDTO
import cccev.f2.unit.domain.model.DataUnitOption
import cccev.f2.unit.domain.model.DataUnitOptionDTO
import cccev.s2.concept.domain.InformationConceptIdentifier
import cccev.s2.requirement.domain.model.RequirementIdentifier
import cccev.s2.unit.domain.DataUnitIdentifier
import cccev.s2.unit.domain.DataUnitOptionIdentifier
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a certification by id, or null if it does not exist. The certification graph will be flattened.
 * @d2 function
 * @parent [cccev.f2.certification.domain.D2CertificationApiPage]
 */
typealias CertificationGetFunction = F2Function<CertificationGetQuery, CertificationGetResult>

/**
 * @d2 query
 * @parent [CertificationGetFunction]
 */
@JsExport
@JsName("CertificationGetQueryDTO")
interface CertificationGetQueryDTO {
    /**
     * Id of the certification to get
     */
    val id: CertificationId
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationGetQuery(
    override val id: CertificationId
): CertificationGetQueryDTO

/**
 * @d2 result
 */
@JsExport
@JsName("CertificationGetResultDTO")
interface CertificationGetResultDTO {
    /**
     * The certification, or null if it does not exist.
     */
    val certification: CertificationFlatDTO?

    /**
     * A map by id of the requirement certifications contained in the fetched certification.
     */
    val requirementCertifications: Map<RequirementCertificationId, RequirementCertificationFlatDTO>

    /**
     * A map by identifier of the requirements contained in the fetched certification.
     */
    val requirements: Map<RequirementIdentifier, RequirementFlatDTO>

    /**
     * A map by identifier of the information concepts contained in the fetched certification.
     */
    val concepts: Map<InformationConceptIdentifier, InformationConceptFlatDTO>

    /**
     * A map by identifier of the data units contained in the fetched certification.
     */
    val units: Map<DataUnitIdentifier, DataUnitFlatDTO>

    /**
     * A map by identifier of the data unit options contained in the fetched certification.
     */
    val unitOptions: Map<DataUnitOptionIdentifier, DataUnitOptionDTO>

    /**
     * A map by id of the supported values contained in the fetched certification.
     */
    val supportedValues: Map<SupportedValueId, SupportedValueFlatDTO>
}

/**
 * @d2 inherit
 */
@Serializable
data class CertificationGetResult(
    override val certification: CertificationFlat?,
    override val requirementCertifications: Map<RequirementCertificationId, RequirementCertificationFlat>,
    override val requirements: Map<RequirementIdentifier, RequirementFlat>,
    override val concepts: Map<InformationConceptIdentifier, InformationConceptFlat>,
    override val units: Map<DataUnitIdentifier, DataUnitFlat>,
    override val unitOptions: Map<DataUnitOptionIdentifier, DataUnitOption>,
    override val supportedValues: Map<SupportedValueId, SupportedValueFlat>
): CertificationGetResultDTO

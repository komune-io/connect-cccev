package cccev.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * The unique id of the evidence.
 * @visual json "0d33dee4-4c31-410a-b54f-e57d18e9c10b"
 * @title DSL/EvidenceId
 * @d2 model
 */
typealias EvidenceId = String

/**
 * The unique identifier of the evidence.
 * @visual json "0d33dee4-4c31-410a-b54f-e57d18e9c10b"
 * @title DSL/EvidenceId
 * @d2 model
 */
typealias EvidenceIdentifier = String

/**
 * Proof that a Requirement is met.
 *
 * The class Evidence provides the means to support responses to Criteria or to a concrete Information Requirement
 * or to an Information Concept inside an Information Requirement. The proof described by an Evidence can: <br/>
 * [1] verify a claim (i.e. is it true that John is 25, yes/no),<br/>
 * [2] prove a condition (i.e. is John 18+, yes/no), or <br/>
 * [3] simply provide data (i.e. the age of a person, namely 25).
 * The proof can be given through documents or extracts of base registries, independently of its structure,
 * format or medium used to exchange it: a pdf document, a video, a recording, etc.
 * @title DSL/Evidence
 * @d2 model
 */
@JsExport
@JsName("EvidenceDTO")
interface EvidenceDTO {
    /**
     * The unique id of the evidence.
     */
    val id: EvidenceId
    /**
     * The unique identifier of the evidence.
     */
    val identifier: EvidenceIdentifier
    /**
     * Evidence Type that specifies characteristics of the Evidence.
     */
    val isConformantTo: List<EvidenceTypeId>
    /**
     * Supported Value that the Evidence contains.
     */
    val supportsValue: List<SupportedValueId>
    /**
     * Information Concept providing facts found/inferred from the Evidence.
     */
    val supportsConcept: List<InformationConceptId>
    /**
     * Requirement for which the Evidence provides proof.
     */
    val supportsRequirement: List<RequirementId>
    /**
     * Period of Time during which the Evidence holds true or has force.
     */
    val validityPeriod: PeriodOfTime?
    /**
     * Name of the Evidence
     */
    val name: String
    /**
     * Proof of evidence
     */
    val file: String?
}

/**
 * @d2 inherit
 */
@Serializable
open class Evidence(
    override val id: EvidenceId,
    override val identifier: EvidenceIdentifier,
    override val isConformantTo: List<EvidenceTypeId> = emptyList(),
    override val supportsValue: List<SupportedValueId> = emptyList(),
    override val supportsConcept: List<InformationConceptId> = emptyList(),
    override val supportsRequirement: List<RequirementId> = emptyList(),
    override val validityPeriod: PeriodOfTime? = null,
    override val name: String = "",
    override val file: String? = null
): EvidenceDTO

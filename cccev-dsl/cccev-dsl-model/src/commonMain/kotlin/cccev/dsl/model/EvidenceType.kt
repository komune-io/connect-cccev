package cccev.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * The unique id of the evidence type list.
 * @visual json "bf92ba66-de62-41dc-af93-bd645b4f5bcb"
 * @parent [EvidenceTypeList]
 * @title EvidenceTypeId
 * @d2 model
 */
typealias EvidenceTypeListId = String

/**
 * The unique id of the evidence type list.
 * @visual json "TheEvidenceTypeListIdentifier"
 * @parent [EvidenceTypeList]
 * @title EvidenceTypeId
 * @d2 hidden
 */
typealias EvidenceTypeListIdentifier = String

/**
 * The unique id of the evidence type.
 * @visual json "082f9b5b-4ffa-4e95-8288-2de2972cade5"
 * @parent [EvidenceType]
 * @title EvidenceTypeId
 * @d2 hidden
 */
typealias EvidenceTypeId = String

/**
 * The unique id of the evidence type.
 * @visual json "TheEvidenceTypeIdentifier"
 * @parent [EvidenceType]
 * @title EvidenceTypeId
 * @d2 model
 */
typealias EvidenceTypeIdentifier = String

/**
 * The EvidenceTypeList defines a list of EvidenceTypes.
 * @d2 model
 * @parent [cccev.dsl.model.d2.D2EvidenceTypeListPage]
 */
@JsExport
@JsName("EvidenceTypeList")
interface EvidenceTypeList {
	val description: String
	val identifier: EvidenceTypeListId
	val name: String
	val specifiesEvidenceType: List<EvidenceType>?
}

/**
 * @d2 inherit
 */
@Serializable
open class EvidenceTypeListBase(
    override val description: String,
    override val identifier: EvidenceTypeListId,
    override val name: String,
    override val specifiesEvidenceType: List<EvidenceTypeBase>? = null
): EvidenceTypeList

/**
 * The EvidenceType defines a type of evidence required to support specific Information Concepts.
 * @d2 model
 * @title DSL/EvidenceType
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
@JsExport
@JsName("EvidenceType")
interface EvidenceType {
	val identifier: EvidenceTypeId
	val name: String
	val supportConcept: List<InformationConceptDTO>
	val evidenceTypeClassification: Code?
	val validityPeriodConstraint: PeriodOfTime?
	val issuingPlace: CoreLocationLocation?
}
@Serializable
open class EvidenceTypeBase(
	override val identifier: EvidenceTypeId,
	override val name: String,
	override val supportConcept: List<InformationConceptDTO>,
	override val evidenceTypeClassification: Code? = null,
	override val validityPeriodConstraint: PeriodOfTime? = null,
	override val issuingPlace: CoreLocationLocation? = null,
): EvidenceType
@Serializable
@JsExport
open class CoreLocationLocation

@JsExport
@Serializable
open class PeriodOfTime(
	val duration: String? = null,
	val endTime: Int? = null,
	val startTime: Int? = null,
)

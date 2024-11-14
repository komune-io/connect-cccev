package cccev.f2.concept.command

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.InformationConceptId
import cccev.f2.concept.D2InformationConceptPage
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Create a new information concept.
 * @d2 function
 * @parent [D2InformationConceptPage]
 * @order 10
 */
typealias InformationConceptCreateFunction = F2Function<InformationConceptCreateCommand, InformationConceptCreatedEvent>

/**
 * @d2 command
 * @parent [InformationConceptCreateFunction]
 */
@JsExport
interface InformationConceptCreateCommandDTO {
    /**
     * Custom identifier of the information concept.
     * @example [cccev.dsl.model.InformationConcept.identifier]
     */
    val identifier: String?

    /**
     * The name of the information concept.
     * @example [cccev.dsl.model.InformationConcept.name]
     */
    val name: String

    /**
     * The data unit used for the information concept.
     * @example [cccev.dsl.model.InformationConcept.unit]
     */
    val hasUnit: DataUnitId

    /**
     * The description of the information concept.
     * @example [cccev.dsl.model.InformationConcept.description]
     */
    val description: String?

    /**
     * Expression to evaluate in order to auto-compute the SupportedValue associated with the information concept, if applicable. <br />
     * For now, the expression will be evaluated using a Kotlin engine. <br />
     * The expression may contain other known information concepts, identified by their id. They must be declared in the `dependsOn` field.
     * @example [cccev.dsl.model.InformationConcept.expressionOfExpectedValue]
     */
    val expressionOfExpectedValue: String?

    /**
     * A list of information concepts the one depends on for auto-computation, if applicable.
     * @example [cccev.dsl.model.InformationConcept.dependsOn]
     */
    val dependsOn: List<InformationConceptId>?
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptCreateCommand(
    override val name: String,
    override val identifier: String?,
    override val hasUnit: DataUnitId,
    override val description: String?,
    override val expressionOfExpectedValue: String?,
    override val dependsOn: List<InformationConceptId>?
): InformationConceptCreateCommandDTO

/**
 * @d2 event
 * @parent [InformationConceptCreateFunction]
 */
@JsExport
interface InformationConceptCreatedEventDTO {
    /**
     * Id of the created information concept.
     */
    val id: InformationConceptId
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptCreatedEvent(
    override val id: InformationConceptId,
): InformationConceptCreatedEventDTO

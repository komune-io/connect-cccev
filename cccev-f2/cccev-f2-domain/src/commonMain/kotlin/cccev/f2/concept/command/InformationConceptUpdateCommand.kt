package cccev.f2.concept.command

import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.d2.D2InformationConceptPage
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Update an information concept.
 * @d2 function
 * @parent [D2InformationConceptPage]
 * @order 20
 */
typealias InformationConceptUpdateFunction = F2Function<InformationConceptUpdateCommand, InformationConceptUpdatedEvent>

/**
 * @d2 command
 * @parent [InformationConceptUpdateFunction]
 */
@JsExport
interface InformationConceptUpdateCommandDTO {
    /**
     * Id of the information concept.
     * @example [cccev.dsl.model.InformationConceptDTO.id]
     */
    val id: InformationConceptId

    /**
     * The name of the information concept.
     * @example [cccev.dsl.model.InformationConceptDTO.name]
     */
    val name: String

    /**
     * The description of the information concept.
     * @example [cccev.dsl.model.InformationConceptDTO.description]
     */
    val description: String?

    /**
     * Expression to evaluate in order to auto-compute the SupportedValue associated with the information concept, if applicable. <br />
     * For now, the expression will be evaluated using a Kotlin engine. <br />
     * The expression may contain other known information concepts, identified by their id. They must be declared in the `dependsOn` field.
     * @example [cccev.dsl.model.InformationConceptDTO.expressionOfExpectedValue]
     */
    val expressionOfExpectedValue: String?

    /**
     * A list of information concepts the one depends on for auto-computation, if applicable.
     * @example [cccev.dsl.model.InformationConceptDTO.dependsOn]
     */
    val dependsOn: List<InformationConceptId>?
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptUpdateCommand(
    override val id: InformationConceptId,
    override val name: String,
    override val description: String?,
    override val expressionOfExpectedValue: String? = null,
    override val dependsOn: List<InformationConceptId>? = null
): InformationConceptUpdateCommandDTO

/**
 * @d2 event
 * @parent [InformationConceptUpdateFunction]
 */
@JsExport
interface InformationConceptUpdatedEventDTO {
    /**
     * Id of the created information concept.
     */
    val id: InformationConceptId
}

/**
 * @d2 inherit
 */
@Serializable
data class InformationConceptUpdatedEvent(
    override val id: InformationConceptId,
): InformationConceptUpdatedEventDTO

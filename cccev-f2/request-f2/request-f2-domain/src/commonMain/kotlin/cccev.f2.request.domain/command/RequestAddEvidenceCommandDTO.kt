package cccev.f2.request.domain.command

import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.request.domain.command.RequestAddedEvidenceEvent
import cccev.s2.request.domain.model.RequestId
import f2.dsl.fnc.F2Function
import io.ktor.http.content.PartData
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Add an evidence to a request.
 * @d2 function
 * @parent [cccev.f2.request.domain.D2RequestApiPage]
 * @child [RequestAddEvidenceCommandDTO]
 * @child [cccev.s2.request.domain.command.RequestAddedEvidenceEventDTO]
 * @order 40
 */
typealias RequestAddEvidenceFunction = F2Function<Pair<RequestAddEvidenceCommandDTOBase, ByteArray>, RequestAddedEvidenceEvent>

/**
 * @d2 command
 */
@JsExport
@JsName("RequestAddEvidenceCommandDTO")
interface RequestAddEvidenceCommandDTO {
    /**
     * Id of the request to add the evidence to.
     */
    val id: RequestId

    /**
     * @ref [cccev.s2.request.domain.model.EvidenceDTO.name]
     */
    val name: String

    /**
     * @ref [cccev.s2.request.domain.model.EvidenceDTO.url]
     */
    val url: String?

    /**
     * @ref [cccev.s2.request.domain.model.EvidenceDTO.isConformantTo]
     */
    val isConformantTo: List<EvidenceTypeId>
}

/**
 * @d2 inherit
 */
data class RequestAddEvidenceCommandDTOBase(
    override val id: RequestId,
    override val name: String,
    override val url: String?,
    override val isConformantTo: List<EvidenceTypeId>
): RequestAddEvidenceCommandDTO

@JsExport
@JsName("RequestAddedEvidenceEventDTO")
interface RequestAddedEvidenceEventDTO: cccev.s2.request.domain.command.RequestAddedEvidenceEventDTO

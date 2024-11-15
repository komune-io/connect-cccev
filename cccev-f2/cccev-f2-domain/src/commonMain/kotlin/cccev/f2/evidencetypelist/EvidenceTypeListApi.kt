package cccev.f2.evidencetypelist

import cccev.f2.evidencetypelist.command.EvidenceTypeListCreateFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetByIdentifierFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetFunction

/**
 * @d2 api
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
interface EvidenceTypeListApi: EvidenceTypeListCommandApi, EvidenceTypeListQueryApi

interface EvidenceTypeListCommandApi {
    fun evidenceTypeListCreate(): EvidenceTypeListCreateFunction
}

interface EvidenceTypeListQueryApi {
    fun evidenceTypeListGet(): EvidenceTypeListGetFunction
    fun evidenceTypeListGetByIdentifier(): EvidenceTypeListGetByIdentifierFunction
}

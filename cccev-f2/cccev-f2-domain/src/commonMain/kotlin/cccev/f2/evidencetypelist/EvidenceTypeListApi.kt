package cccev.f2.evidencetypelist

import cccev.f2.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetByIdentifierFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction
import cccev.f2.evidencetypelist.command.EvidenceTypeListCreateFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetByIdentifierFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetFunction

/**
 * @d2 api
 * @parent [cccev.core.evidencetypelist.D2EvidenceListTypePage]
 */
interface EvidenceTypeListApi: EvidenceTypeListCommandApi, EvidenceTypeListQueryApi

interface EvidenceTypeListCommandApi {
    fun evidenceTypeListCreate(): EvidenceTypeListCreateFunction
}

interface EvidenceTypeListQueryApi {
    fun evidenceTypeListGet(): EvidenceTypeListGetFunction
    fun evidenceTypeListGetByIdentifier(): EvidenceTypeListGetByIdentifierFunction
}
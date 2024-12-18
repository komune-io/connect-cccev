package cccev.f2.evidencetype

import cccev.f2.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetByIdentifierFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction

/**
 * @d2 api
 * @parent [cccev.dsl.model.d2.D2EvidenceTypePage]
 */
interface EvidenceTypeApi: EvidenceTypeCommandApi, EvidenceTypeQueryApi

interface EvidenceTypeCommandApi {
    fun evidenceTypeCreate(): EvidenceTypeCreateFunction
}

interface EvidenceTypeQueryApi {
    fun evidenceTypeGet(): EvidenceTypeGetFunction
    fun evidenceTypeGetByIdentifier(): EvidenceTypeGetByIdentifierFunction
}

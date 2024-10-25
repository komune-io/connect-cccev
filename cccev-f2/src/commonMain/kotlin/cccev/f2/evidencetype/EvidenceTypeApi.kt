package cccev.f2.evidencetype

import cccev.core.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction

/**
 * @d2 api
 * @parent [cccev.core.evidencetype.D2EvidenceTypePage]
 */
interface EvidenceTypeApi: EvidenceTypeCommandApi, EvidenceTypeQueryApi

interface EvidenceTypeCommandApi {
    fun evidenceTypeCreate(): EvidenceTypeCreateFunction
}

interface EvidenceTypeQueryApi {
    fun evidenceTypeGet(): EvidenceTypeGetFunction
}

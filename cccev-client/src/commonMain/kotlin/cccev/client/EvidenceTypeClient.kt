package cccev.client

import cccev.f2.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.evidencetype.EvidenceTypeApi
import cccev.f2.evidencetype.query.EvidenceTypeGetByIdentifierFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport

fun F2Client.evidenceTypeClient(): F2SupplierSingle<EvidenceTypeClient> = f2SupplierSingle {
    EvidenceTypeClient(this)
}

fun evidenceTypeClient(urlBase: String): F2SupplierSingle<EvidenceTypeClient> = f2SupplierSingle {
    EvidenceTypeClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
open class EvidenceTypeClient constructor(private val client: F2Client): EvidenceTypeApi {
    override fun evidenceTypeCreate(): EvidenceTypeCreateFunction
        = client.function(this::evidenceTypeCreate.name)
    override fun evidenceTypeGet(): EvidenceTypeGetFunction
        = client.function(this::evidenceTypeGet.name)
    override fun evidenceTypeGetByIdentifier(): EvidenceTypeGetByIdentifierFunction
        = client.function(this::evidenceTypeGetByIdentifier.name)
}

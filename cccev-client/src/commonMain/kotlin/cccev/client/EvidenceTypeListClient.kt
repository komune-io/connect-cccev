package cccev.client

import cccev.f2.evidencetype.EvidenceTypeApi
import cccev.f2.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetByIdentifierFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction
import cccev.f2.evidencetypelist.EvidenceTypeListApi
import cccev.f2.evidencetypelist.command.EvidenceTypeListCreateFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetByIdentifierFunction
import cccev.f2.evidencetypelist.query.EvidenceTypeListGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport

fun F2Client.evidenceTypeListClient(): F2SupplierSingle<EvidenceTypeListClient> = f2SupplierSingle {
    EvidenceTypeListClient(this)
}

fun evidenceTypeListClient(urlBase: String): F2SupplierSingle<EvidenceTypeListClient> = f2SupplierSingle {
    EvidenceTypeListClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
open class EvidenceTypeListClient(private val client: F2Client): EvidenceTypeListApi {

    override fun evidenceTypeListCreate(): EvidenceTypeListCreateFunction
            = client.function(this::evidenceTypeListCreate.name)

    override fun evidenceTypeListGet(): EvidenceTypeListGetFunction
            = client.function(this::evidenceTypeListGet.name)

    override fun evidenceTypeListGetByIdentifier(): EvidenceTypeListGetByIdentifierFunction
            = client.function(this::evidenceTypeListGetByIdentifier.name)
}

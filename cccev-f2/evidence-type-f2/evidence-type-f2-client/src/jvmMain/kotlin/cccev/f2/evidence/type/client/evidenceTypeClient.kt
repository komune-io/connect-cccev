package cccev.f2.evidence.type.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle

actual fun F2Client.evidenceTypeClient(): F2SupplierSingle<EvidenceTypeClient> = f2SupplierSingle {
    EvidenceTypeClient(this)
}


actual fun evidenceTypeClient(urlBase: String): F2SupplierSingle<EvidenceTypeClient> = f2SupplierSingle {
    EvidenceTypeClient(
        F2ClientBuilder.get(urlBase)
    )
}

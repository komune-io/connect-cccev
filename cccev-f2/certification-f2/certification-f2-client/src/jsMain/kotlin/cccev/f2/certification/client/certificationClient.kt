package cccev.f2.certification.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
@JsName("requestClient")
actual fun certificationClient(urlBase: String): F2SupplierSingle<CertificationClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .await()
        .let(::CertificationClient)
}

actual fun F2Client.certificationClient(): F2SupplierSingle<CertificationClient> = f2SupplierSingle {
    CertificationClient(this)
}

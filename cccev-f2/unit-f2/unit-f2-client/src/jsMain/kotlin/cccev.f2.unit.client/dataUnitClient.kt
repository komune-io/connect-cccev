package cccev.f2.unit.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
@JsName("dataUnitClient")
actual fun dataUnitClient(urlBase: String): F2SupplierSingle<DataUnitClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .await()
        .let(::DataUnitClient)
}

actual fun F2Client.dataUnitClient(): F2SupplierSingle<DataUnitClient> = f2SupplierSingle {
    DataUnitClient(this)
}

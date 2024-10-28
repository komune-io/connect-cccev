package cccev.client

import cccev.f2.unit.DataUnitApi
import cccev.f2.unit.command.DataUnitCreateFunction
import cccev.f2.unit.command.DataUnitUpdateFunction
import cccev.f2.unit.query.DataUnitGetByIdentifierFunction
import cccev.f2.unit.query.DataUnitGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport

fun F2Client.dataUnitClient(): F2SupplierSingle<DataUnitClient> = f2SupplierSingle {
    DataUnitClient(this)
}

fun dataUnitClient(urlBase: String): F2SupplierSingle<DataUnitClient> = f2SupplierSingle {
    DataUnitClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
open class DataUnitClient constructor(private val client: F2Client): DataUnitApi {
    override fun dataUnitGet(): DataUnitGetFunction = client.function(this::dataUnitGet.name)
    override fun dataUnitGetByIdentifier(): DataUnitGetByIdentifierFunction
            = client.function(this::dataUnitGetByIdentifier.name)

    override fun dataUnitCreate(): DataUnitCreateFunction  = client.function(this::dataUnitCreate.name)
    override fun dataUnitUpdate(): DataUnitUpdateFunction  = client.function(this::dataUnitUpdate.name)
}

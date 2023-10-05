package cccev.f2.unit.client

import cccev.f2.unit.domain.DataUnitApi
import cccev.f2.unit.domain.command.DataUnitCreateFunction
import cccev.f2.unit.domain.query.DataUnitGetByIdentifierFunction
import cccev.f2.unit.domain.query.DataUnitGetFunction
import f2.client.F2Client
import f2.client.function
import f2.dsl.fnc.F2SupplierSingle
import kotlin.js.JsExport
import kotlin.js.JsName

expect fun F2Client.dataUnitClient(): F2SupplierSingle<DataUnitClient>
expect fun dataUnitClient(urlBase: String): F2SupplierSingle<DataUnitClient>

@JsExport
@JsName("DataUnitClient")
open class DataUnitClient constructor(private val client: F2Client): DataUnitApi {
    override fun dataUnitGet(): DataUnitGetFunction = client.function(this::dataUnitGet.name)
    override fun dataUnitGetByIdentifier(): DataUnitGetByIdentifierFunction
            = client.function(this::dataUnitGetByIdentifier.name)

    override fun dataUnitCreate(): DataUnitCreateFunction  = client.function(this::dataUnitCreate.name)
}

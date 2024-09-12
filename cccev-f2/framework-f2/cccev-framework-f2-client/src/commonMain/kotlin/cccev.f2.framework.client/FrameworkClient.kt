package cccev.f2.framework.client

import cccev.f2.framework.domain.FrameworkApi
import cccev.f2.framework.domain.command.FrameworkCreateFunction
import cccev.f2.framework.domain.query.FrameworkGetByIdentifierFunction
import cccev.f2.framework.domain.query.FrameworkGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport
import kotlin.js.JsName

fun F2Client.frameworkClient(): F2SupplierSingle<FrameworkClient> = f2SupplierSingle {
    FrameworkClient(this)
}

fun frameworkClient(urlBase: String): F2SupplierSingle<FrameworkClient> = f2SupplierSingle {
    FrameworkClient(
        F2ClientBuilder.get(urlBase)
    )
}
@JsExport
@JsName("FrameworkClient")
open class FrameworkClient constructor(private val client: F2Client): FrameworkApi {
    override fun frameworkCreate(): FrameworkCreateFunction  = client.function(this::frameworkCreate.name)
    override fun frameworkGet(): FrameworkGetFunction = client.function(this::frameworkGet.name)
    override fun frameworkGetByIdentifier(): FrameworkGetByIdentifierFunction
            = client.function(this::frameworkGetByIdentifier.name)
}

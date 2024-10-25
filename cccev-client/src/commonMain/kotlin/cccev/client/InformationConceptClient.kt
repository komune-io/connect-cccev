package cccev.client

import cccev.core.concept.command.InformationConceptCreateFunction
import cccev.core.concept.command.InformationConceptUpdateFunction
import cccev.f2.concept.InformationConceptApi
import cccev.f2.concept.query.InformationConceptGetByIdentifierFunction
import cccev.f2.concept.query.InformationConceptGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport

fun F2Client.informationConceptClient(): F2SupplierSingle<InformationConceptClient> = f2SupplierSingle {
    InformationConceptClient(this)
}

fun informationConceptClient(urlBase: String): F2SupplierSingle<InformationConceptClient> = f2SupplierSingle {
    InformationConceptClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
open class InformationConceptClient constructor(private val client: F2Client): InformationConceptApi {
    override fun conceptCreate(): InformationConceptCreateFunction = client.function(this::conceptCreate.name)
    override fun conceptUpdate(): InformationConceptUpdateFunction = client.function(this::conceptUpdate.name)
    override fun conceptGet(): InformationConceptGetFunction = client.function(this::conceptGet.name)
    override fun conceptGetByIdentifier(): InformationConceptGetByIdentifierFunction = client.function(this::conceptGetByIdentifier.name)
}

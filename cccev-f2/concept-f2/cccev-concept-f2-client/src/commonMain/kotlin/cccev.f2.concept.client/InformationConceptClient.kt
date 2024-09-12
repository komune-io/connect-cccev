package cccev.f2.concept.client

import cccev.f2.concept.domain.InformationConceptApi
import cccev.f2.concept.domain.command.InformationConceptCreateFunction
import cccev.f2.concept.domain.command.InformationConceptUpdateFunction
import cccev.f2.concept.domain.query.InformationConceptGetByIdentifierFunction
import cccev.f2.concept.domain.query.InformationConceptGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport
import kotlin.js.JsName

fun F2Client.informationConceptClient(): F2SupplierSingle<InformationConceptClient> = f2SupplierSingle {
    InformationConceptClient(this)
}

fun informationConceptClient(urlBase: String): F2SupplierSingle<InformationConceptClient> = f2SupplierSingle {
    InformationConceptClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
@JsName("InformationConceptClient")
open class InformationConceptClient constructor(private val client: F2Client) : InformationConceptApi {
    override fun conceptCreate(): InformationConceptCreateFunction  = client.function(this::conceptCreate.name)
    override fun conceptUpdate(): InformationConceptUpdateFunction  = client.function(this::conceptUpdate.name)
    override fun conceptGet(): InformationConceptGetFunction = client.function(this::conceptGet.name)
    override fun conceptGetByIdentifier(): InformationConceptGetByIdentifierFunction = client.function(this::conceptGetByIdentifier.name)
}

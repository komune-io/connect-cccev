package cccev.client

import cccev.core.certification.command.CertificationAddRequirementsFunction
import cccev.core.certification.command.CertificationCreateFunction
import cccev.core.certification.command.CertificationFillValuesFunction
import cccev.core.certification.command.CertificationRemoveRequirementsFunction
import cccev.f2.certification.CertificationApi
import cccev.f2.certification.query.CertificationGetFunction
import f2.client.F2Client
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import kotlin.js.JsExport

fun F2Client.certificationClient(): F2SupplierSingle<CertificationClient> = f2SupplierSingle {
    CertificationClient(this)
}

fun certificationClient(
    urlBase: String
): F2SupplierSingle<CertificationClient> = f2SupplierSingle {
    CertificationClient(
        F2ClientBuilder.get(urlBase)
    )
}

@JsExport
open class CertificationClient constructor(val client: F2Client): CertificationApi {
    override fun certificationGet(): CertificationGetFunction
        = client.function(this::certificationGet.name)
    override fun certificationCreate(): CertificationCreateFunction
        = client.function(this::certificationCreate.name)
    override fun certificationAddRequirements(): CertificationAddRequirementsFunction
        = client.function(this::certificationAddRequirements.name)
    override fun certificationRemoveRequirements(): CertificationRemoveRequirementsFunction
        = client.function(this::certificationRemoveRequirements.name)
    override fun certificationFillValues(): CertificationFillValuesFunction
        = client.function(this::certificationFillValues.name)
//    override fun certificationRemoveEvidence(): CertificationRemoveEvidenceFunction
//        = client.function(this::certificationRemoveEvidence.name)
}

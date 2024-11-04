package cccev.dsl.client

import cccev.client.CertificationClient
import cccev.client.DataUnitClient
import cccev.client.EvidenceTypeClient
import cccev.client.InformationConceptClient
import cccev.client.RequirementClient
import cccev.client.certificationClient
import cccev.client.dataUnitClient
import cccev.client.evidenceTypeClient
import cccev.client.evidenceTypeListClient
import cccev.client.informationConceptClient
import cccev.client.requirementClient
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.common.F2ClientConfigLambda

class CCCEVClient(
    val evidenceTypeClient: EvidenceTypeClient,
    val informationConceptClient: InformationConceptClient,
    val certificationClient: CertificationClient,
    val requirementClient: RequirementClient,
    val dataUnitClient: DataUnitClient,
    val graphClient: CCCEVGraphClient
) {
    companion object {
        suspend operator fun invoke(
            url: String,
            config: F2ClientConfigLambda<*>? = null
        ): CCCEVClient {
            val f2Client = F2ClientBuilder.get(url, config = config)
            val evidenceTypeListClient = f2Client.evidenceTypeListClient().invoke()
            val evidenceTypeClient = f2Client.evidenceTypeClient().invoke()
            val informationConceptClient = f2Client.informationConceptClient().invoke()
            val certificationClient =  f2Client.certificationClient().invoke()
            val requirementClient = f2Client.requirementClient().invoke()
            val dataUnitClient = f2Client.dataUnitClient().invoke()
            return CCCEVClient(
                evidenceTypeClient = evidenceTypeClient,
                informationConceptClient = informationConceptClient,
                certificationClient = certificationClient,
                requirementClient = requirementClient,
                dataUnitClient = dataUnitClient,
                CCCEVGraphClient(
                    evidenceTypeListClient = evidenceTypeListClient,
                    evidenceTypeClient = evidenceTypeClient,
                    informationConceptClient = informationConceptClient,
                    requirementClient = requirementClient,
                    dataUnitClient = dataUnitClient,
                )
            )
        }
    }

}

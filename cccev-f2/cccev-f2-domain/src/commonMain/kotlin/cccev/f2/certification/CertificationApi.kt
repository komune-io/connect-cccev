package cccev.f2.certification

import cccev.f2.certification.command.CertificationAddRequirementsFunction
import cccev.f2.certification.command.CertificationCreateFunction
import cccev.f2.certification.command.CertificationFillValuesFunction
import cccev.f2.certification.command.CertificationRemoveRequirementsFunction
import cccev.f2.certification.query.CertificationGetFunction

/**
 * @d2 api
 * @parent [cccev.dsl.model.d2.D2CertificationPage]
 */
interface CertificationApi: CertificationQueryApi, CertificationCommandApi

interface CertificationQueryApi {
    /** Get certification */
    fun certificationGet(): CertificationGetFunction
}

interface CertificationCommandApi {
    /** Create a request */
    fun certificationCreate(): CertificationCreateFunction

    /** Add requirements to a request */
    fun certificationAddRequirements(): CertificationAddRequirementsFunction

    /** Remove requirements from a request */
    fun certificationRemoveRequirements(): CertificationRemoveRequirementsFunction

    /** Add values to a request */
    fun certificationFillValues(): CertificationFillValuesFunction

    /** Remove an evidence from a request */
//    fun certificationRemoveEvidence(): CertificationRemoveEvidenceFunction
}

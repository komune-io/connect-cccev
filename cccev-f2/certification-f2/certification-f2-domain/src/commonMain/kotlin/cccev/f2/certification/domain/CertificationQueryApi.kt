package cccev.f2.certification.domain

import cccev.f2.certification.domain.query.CertificationGetFunction

interface CertificationQueryApi {
    /** Get request */
    fun certificationGet(): CertificationGetFunction
}

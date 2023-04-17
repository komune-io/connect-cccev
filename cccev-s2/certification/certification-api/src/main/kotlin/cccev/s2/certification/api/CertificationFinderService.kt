package cccev.s2.certification.api

import cccev.projection.api.entity.certification.CertificationRepository
import cccev.s2.certification.api.entity.toCertification
import cccev.s2.certification.domain.model.Certification
import cccev.s2.certification.domain.model.CertificationId
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class CertificationFinderService(
    private val certificationRepository: CertificationRepository
) {
    suspend fun getOrNull(id: CertificationId): Certification? {
        return certificationRepository.findById(id)
            .awaitSingleOrNull()
            ?.toCertification()
    }

    suspend fun get(id: CertificationId): Certification {
        return getOrNull(id) ?: throw NotFoundException("Certification", id)
    }
}

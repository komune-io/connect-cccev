package cccev.core.certification

import cccev.core.certification.entity.Certification
import cccev.core.certification.entity.CertificationRepository
import cccev.f2.certification.model.CertificationId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CertificationFinderService(
    private val certificationRepository: CertificationRepository
) {
    suspend fun getOrNull(id: CertificationId): Certification? {
        return certificationRepository.findById(id)
    }

    suspend fun get(id: CertificationId): Certification {
        return certificationRepository.findById(id)
            ?: throw NotFoundException("Certification", id)
    }
}

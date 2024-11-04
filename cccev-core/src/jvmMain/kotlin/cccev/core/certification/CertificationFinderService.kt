package cccev.core.certification

import cccev.core.certification.entity.CertificationEntity
import cccev.core.certification.entity.CertificationRepository
import cccev.dsl.model.CertificationId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CertificationFinderService(
    private val certificationRepository: CertificationRepository
) {
    suspend fun getOrNull(id: CertificationId): CertificationEntity? {
        return certificationRepository.findById(id)
    }

    suspend fun get(id: CertificationId): CertificationEntity {
        return certificationRepository.findById(id)
            ?: throw NotFoundException("Certification", id)
    }
}

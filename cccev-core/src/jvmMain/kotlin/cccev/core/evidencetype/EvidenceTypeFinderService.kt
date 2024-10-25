package cccev.core.evidencetype

import cccev.core.evidencetype.entity.EvidenceType
import cccev.core.evidencetype.entity.EvidenceTypeRepository
import cccev.core.evidencetype.model.EvidenceTypeId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class EvidenceTypeFinderService(
    private val evidenceTypeRepository: EvidenceTypeRepository
) {
    suspend fun getOrNull(id: EvidenceTypeId): EvidenceType? {
        return evidenceTypeRepository.findById(id)
    }

    suspend fun get(id: EvidenceTypeId): EvidenceType {
        return evidenceTypeRepository.findById(id)
            ?: throw NotFoundException("EvidenceType", id)
    }
}

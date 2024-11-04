package cccev.core.evidencetype

import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.core.evidencetype.entity.EvidenceTypeRepository
import cccev.dsl.model.EvidenceTypeId
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class EvidenceTypeFinderService(
    private val evidenceTypeRepository: EvidenceTypeRepository
) {
    suspend fun getOrNull(id: EvidenceTypeId): EvidenceTypeEntity? {
        return evidenceTypeRepository.findById(id)
    }
    suspend fun getOrNullByIdentifier(identifier: EvidenceTypeId): EvidenceTypeEntity? {
        return evidenceTypeRepository.findByIdentifier(identifier)
    }

    suspend fun get(id: EvidenceTypeId): EvidenceTypeEntity {
        return evidenceTypeRepository.findById(id)
            ?: throw NotFoundException("EvidenceType", id)
    }
}

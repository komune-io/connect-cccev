package cccev.s2.evidence.api

import cccev.projection.api.entity.evidencetype.EvidenceTypeRepository
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListRepository
import cccev.s2.evidence.api.entity.list.toEvidenceTypeList
import cccev.s2.evidence.api.entity.type.toEvidenceType
import cccev.s2.evidence.domain.EvidenceTypeFinder
import cccev.s2.evidence.domain.EvidenceTypeId
import cccev.s2.evidence.domain.EvidenceTypeListId
import cccev.s2.evidence.domain.model.EvidenceType
import cccev.s2.evidence.domain.model.EvidenceTypeList
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class EvidenceTypeFinderService(
    private val evidenceTypeListRepository: EvidenceTypeListRepository,
    private val evidenceTypeRepository: EvidenceTypeRepository
): EvidenceTypeFinder {
    override suspend fun getOrNull(id: EvidenceTypeId): EvidenceType? {
        return evidenceTypeRepository.findById(id)
            .awaitSingleOrNull()
            ?.toEvidenceType()
    }

    override suspend fun get(id: EvidenceTypeId): EvidenceType {
        return getOrNull(id) ?: throw NotFoundException("EvidenceType", id)
    }

    override suspend fun getListOrNull(id: EvidenceTypeListId): EvidenceTypeList? {
        return evidenceTypeListRepository.findById(id)
            .awaitSingleOrNull()
            ?.toEvidenceTypeList()
    }

    override suspend fun getList(id: EvidenceTypeListId): EvidenceTypeList {
        return getListOrNull(id) ?: throw NotFoundException("EvidenceTypeList", id)
    }
}

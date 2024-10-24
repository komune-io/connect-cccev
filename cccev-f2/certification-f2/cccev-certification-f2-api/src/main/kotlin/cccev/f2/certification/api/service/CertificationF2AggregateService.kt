package cccev.f2.certification.api.service

import cccev.core.certification.CertificationAggregateService
import cccev.f2.certification.domain.command.CertificationAddRequirementsCommand
import cccev.f2.certification.domain.command.CertificationAddedRequirementsEvent
import cccev.f2.certification.domain.command.CertificationCreateCommand
import cccev.f2.certification.domain.command.CertificationCreatedEvent
import cccev.f2.certification.domain.command.CertificationFillValuesCommand
import cccev.f2.certification.domain.command.CertificationFilledValuesEvent
import cccev.f2.certification.domain.command.CertificationRemoveRequirementsCommand
import cccev.f2.certification.domain.command.CertificationRemovedRequirementsEvent
import io.komune.fs.s2.file.client.FileClient
import org.springframework.stereotype.Service

@Service
class CertificationF2AggregateService(
    private val fileClient: FileClient,
    private val certificationAggregateService: CertificationAggregateService
) {
    suspend fun create(command: CertificationCreateCommand): CertificationCreatedEvent {
        return certificationAggregateService.create(command)
    }

    suspend fun addRequirements(command: CertificationAddRequirementsCommand): CertificationAddedRequirementsEvent {
        return certificationAggregateService.addRequirements(command)
    }

    suspend fun removeRequirements(command: CertificationRemoveRequirementsCommand): CertificationRemovedRequirementsEvent {
        return certificationAggregateService.removeRequirements(command)
    }

    suspend fun fillValues(command: CertificationFillValuesCommand): CertificationFilledValuesEvent {
        return certificationAggregateService.fillValues(command)
    }

//    suspend fun addEvidence(command: CertificationAddEvidenceCommandDTOBase, file: FilePart?): CertificationAddedEvidenceEvent {
//        val filePath = file?.upload(command.id, CertificationFsPath.DIR_EVIDENCE, command.metadata, command?.vectorize)?.path
//        return CertificationAddEvidenceCommand(
//            id = command.id,
//            name = command.name,
//            file = filePath,
//            url = command.url,
//            isConformantTo = command.isConformantTo,
//            supportsConcept = command.supportsConcept,
//        ).let { certificationAggregateService.addEvidence(it) }
//    }
//
//    suspend fun removeEvidence(command: CertificationRemoveEvidenceCommand): CertificationRemovedEvidenceEvent {
//        return certificationAggregateService.removeEvidence(command)
//    }

//    private suspend fun FilePart.upload(
//        certificationId: CertificationId,
//        directory: String,
//        metadata: Map<String, String>?,
//        vectorize: Boolean?
//    ): FileUploadedEvent {
//        val path = FilePath(
//            objectType = CertificationFsPath.OBJECT_TYPE,
//            objectId = certificationId,
//            directory = directory,
//            name = filename(),
//        )
//        return fileClient.fileUpload(
//            command = path.toUploadCommand(
//                metadata = metadata ?: emptyMap(),
//                vectorize = vectorize ?: false
//            ),
//            file = contentByteArray()
//        )
//    }
}

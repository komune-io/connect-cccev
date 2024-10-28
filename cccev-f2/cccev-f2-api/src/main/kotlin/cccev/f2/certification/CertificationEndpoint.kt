package cccev.f2.certification

import org.slf4j.LoggerFactory

import cccev.core.certification.CertificationAggregateService
import cccev.core.certification.CertificationFinderService
import cccev.f2.certification.command.CertificationAddRequirementsFunction
import cccev.f2.certification.command.CertificationCreateFunction
import cccev.f2.certification.command.CertificationFillValuesFunction
import cccev.f2.certification.command.CertificationRemoveRequirementsFunction
import cccev.f2.CccevFlatGraph
import cccev.f2.certification.model.flattenTo
import cccev.f2.certification.query.CertificationGetFunction
import cccev.f2.certification.query.CertificationGetResult
import io.komune.fs.s2.file.client.FileClient
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @d2 api
 * @parent [D2RequestApiPage]
 */
@RestController
@RequestMapping
class CertificationEndpoint(
    private val fileClient: FileClient,
    private val certificationAggregateService: CertificationAggregateService,
    private val certificationFinderService: CertificationFinderService
): CertificationApi {
    private val logger = LoggerFactory.getLogger(CertificationEndpoint::class.java)

    @Bean
    override fun certificationGet(): CertificationGetFunction = f2Function { query ->
        logger.info("certificationGet: $query")
        val graph = CccevFlatGraph().also {
            certificationFinderService.getOrNull(query.id)?.flattenTo(it)
        }
        CertificationGetResult(
            certification = graph.certifications[query.id],
            graph = graph
        )
    }

//    /** Download an evidence of a certification */
//    @PostMapping("/certificationDownloadEvidence")
//    suspend fun certificationDownloadEvidence(
//        @RequestBody query: CertificationDownloadEvidenceQueryDTOBase,
//    ) = serveFile(fileClient) {
//        logger.info("certificationDownloadEvidence: $query")
//        val certification = certificationFinderService.get(query.id)
//        val evidence = certification.evidences.values.flatten().firstOrNull { it.id == query.evidenceId }
//            ?: throw NotFoundException("Evidence", query.evidenceId)
//
//        evidence.file
//    }

    @Bean
    override fun certificationCreate(): CertificationCreateFunction = f2Function { command ->
        logger.info("certificationCreate: $command")
        certificationAggregateService.create(command)
    }

    @Bean
    override fun certificationAddRequirements(): CertificationAddRequirementsFunction = f2Function { command ->
        logger.info("certificationAddRequirements: $command")
        certificationAggregateService.addRequirements(command)
    }

    @Bean
    override fun certificationRemoveRequirements(): CertificationRemoveRequirementsFunction = f2Function { command ->
        logger.info("certificationRemoveRequirements: $command")
        certificationAggregateService.removeRequirements(command)
    }

    @Bean
    override fun certificationFillValues(): CertificationFillValuesFunction = f2Function { command ->
        logger.info("certificationFillValues: $command")
        certificationAggregateService.fillValues(command)
    }

//    /** Add an evidence to a certification */
//    @PostMapping("/certificationAddEvidence")
//    suspend fun certificationAddEvidence(
//        @RequestPart("command") command: CertificationAddEvidenceCommandDTOBase,
//        @RequestPart("file", required = false) file: FilePart?,
//    ): CertificationAddedEvidenceEvent {
//        logger.info("certificationAddEvidence: $command")
//        return certificationAggregateService.addEvidence(command, file)
//    }

//    @Bean
//    override fun certificationRemoveEvidence(): CertificationRemoveEvidenceFunction = f2Function { command ->
//        logger.info("certificationRemoveEvidence: $command")
//        certificationAggregateService.removeEvidence(command)
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

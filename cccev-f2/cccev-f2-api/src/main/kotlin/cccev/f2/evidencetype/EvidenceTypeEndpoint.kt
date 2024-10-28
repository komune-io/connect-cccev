package cccev.f2.evidencetype

import cccev.core.evidencetype.EvidenceTypeAggregateService
import cccev.core.evidencetype.EvidenceTypeFinderService
import cccev.f2.evidencetype.command.EvidenceTypeCreateFunction
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.InformationConceptEndpoint
import cccev.f2.evidencetype.model.flattenTo
import cccev.f2.evidencetype.query.EvidenceTypeGetFunction
import cccev.f2.evidencetype.query.EvidenceTypeGetResult
import f2.dsl.fnc.f2Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @parent [D2EvidenceTypePage]
 */
@Configuration
class EvidenceTypeEndpoint(
    private val evidenceTypeAggregateService: EvidenceTypeAggregateService,
    private val evidenceTypeFinderService: EvidenceTypeFinderService
): EvidenceTypeApi {
    private val logger = LoggerFactory.getLogger(InformationConceptEndpoint::class.java)

    @Bean
    override fun evidenceTypeCreate(): EvidenceTypeCreateFunction = f2Function { command ->
        logger.info("evidenceTypeCreate: $command")
        evidenceTypeAggregateService.create(command)
    }

    @Bean
    override fun evidenceTypeGet(): EvidenceTypeGetFunction = f2Function { query ->
        logger.info("evidenceTypeGet: $query")

        val evidenceType = evidenceTypeFinderService.getOrNull(query.id)
        val graph = CccevFlatGraph().also { evidenceType?.flattenTo(it) }

        EvidenceTypeGetResult(
            item = graph.evidenceTypes[evidenceType?.id],
            graph = graph
        )
    }
}

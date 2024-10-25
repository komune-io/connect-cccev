package cccev.f2.concept

import cccev.core.concept.InformationConceptAggregateService
import cccev.core.concept.InformationConceptFinderService
import cccev.core.concept.command.InformationConceptCreateFunction
import cccev.core.concept.command.InformationConceptUpdateFunction
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.model.flattenTo
import cccev.f2.concept.query.InformationConceptGetByIdentifierFunction
import cccev.f2.concept.query.InformationConceptGetByIdentifierResult
import cccev.f2.concept.query.InformationConceptGetFunction
import cccev.f2.concept.query.InformationConceptGetResult
import f2.dsl.fnc.f2Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @parent [cccev.core.concept.D2InformationConceptPage]
 */
@Configuration
class InformationConceptEndpoint(
    private val informationConceptAggregateService: InformationConceptAggregateService,
    private val informationConceptFinderService: InformationConceptFinderService
): InformationConceptApi {
    private val logger = LoggerFactory.getLogger(InformationConceptEndpoint::class.java)

    @Bean
    override fun conceptGet(): InformationConceptGetFunction = f2Function { query ->
        logger.info("conceptGet: $query")
        val concept = informationConceptFinderService.getOrNull(query.id)
        val graph = CccevFlatGraph().also { concept?.flattenTo(it) }

        InformationConceptGetResult(
            item = graph.concepts[concept?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun conceptGetByIdentifier(): InformationConceptGetByIdentifierFunction = f2Function { query ->
        logger.info("conceptGetByIdentifier: $query")
        val concept = informationConceptFinderService.getByIdentifierOrNull(query.identifier)
        val graph = CccevFlatGraph().also { concept?.flattenTo(it) }

        InformationConceptGetByIdentifierResult(
            item = graph.concepts[concept?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun conceptCreate(): InformationConceptCreateFunction = f2Function { command ->
        logger.info("conceptCreate: $command")
        informationConceptAggregateService.create(command)
    }

    @Bean
    override fun conceptUpdate(): InformationConceptUpdateFunction = f2Function { command ->
        logger.info("conceptUpdate: $command")
        informationConceptAggregateService.update(command)
    }
}

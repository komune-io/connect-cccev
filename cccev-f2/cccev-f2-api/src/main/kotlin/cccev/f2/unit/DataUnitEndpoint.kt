package cccev.f2.unit

import cccev.core.unit.DataUnitAggregateService
import cccev.core.unit.DataUnitFinderService
import cccev.f2.CccevFlatGraph
import cccev.f2.unit.command.DataUnitCreateFunction
import cccev.f2.unit.command.DataUnitUpdateFunction
import cccev.f2.unit.model.flattenTo
import cccev.f2.unit.query.DataUnitGetByIdentifierFunction
import cccev.f2.unit.query.DataUnitGetByIdentifierResult
import cccev.f2.unit.query.DataUnitGetFunction
import cccev.f2.unit.query.DataUnitGetResult
import f2.dsl.fnc.f2Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 api
 * @parent [cccev.core.unit.D2DataUnitPage]
 */
@Configuration
class DataUnitEndpoint(
    private val dataUnitAggregateService: DataUnitAggregateService,
    private val dataUnitFinderService: DataUnitFinderService
): DataUnitApi {
    private val logger = LoggerFactory.getLogger(DataUnitEndpoint::class.java)

    @Bean
    override fun dataUnitGet(): DataUnitGetFunction = f2Function { query ->
        logger.info("dataUnitGet: $query")
        val unit = dataUnitFinderService.getOrNull(query.id)
        val graph = CccevFlatGraph().also { unit?.flattenTo(it) }

        DataUnitGetResult(
            item = graph.units[unit?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun dataUnitGetByIdentifier(): DataUnitGetByIdentifierFunction = f2Function { query ->
        logger.info("dataUnitGet: $query")
        val unit = dataUnitFinderService.getByIdentifierOrNull(query.identifier)
        val graph = CccevFlatGraph().also { unit?.flattenTo(it) }

        DataUnitGetByIdentifierResult(
            item = graph.units[unit?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun dataUnitCreate(): DataUnitCreateFunction = f2Function { command ->
        logger.info("dataUnitCreate: $command")
        dataUnitAggregateService.create(command)
    }

    @Bean
    override fun dataUnitUpdate(): DataUnitUpdateFunction = f2Function { command ->
        logger.info("dataUnitUpdate: $command")
        dataUnitAggregateService.update(command)
    }
}

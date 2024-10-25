package cccev.f2.requirement

import cccev.core.requirement.RequirementAggregateService
import cccev.core.requirement.RequirementFinderService
import cccev.core.requirement.command.RequirementAddConceptsFunction
import cccev.core.requirement.command.RequirementAddEvidenceTypesFunction
import cccev.core.requirement.command.RequirementAddRequirementsFunction
import cccev.core.requirement.command.RequirementCreateFunction
import cccev.core.requirement.command.RequirementRemoveConceptsFunction
import cccev.core.requirement.command.RequirementRemoveEvidenceTypesFunction
import cccev.core.requirement.command.RequirementRemoveRequirementsFunction
import cccev.core.requirement.command.RequirementUpdateFunction
import cccev.f2.CccevFlatGraph
import cccev.f2.concept.InformationConceptEndpoint
import cccev.f2.requirement.model.flattenTo
import cccev.f2.requirement.query.RequirementGetByIdentifierFunction
import cccev.f2.requirement.query.RequirementGetByIdentifierResult
import cccev.f2.requirement.query.RequirementGetFunction
import cccev.f2.requirement.query.RequirementGetResult
import f2.dsl.fnc.f2Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RequirementEndpoint(
    private val requirementAggregateService: RequirementAggregateService,
    private val requirementFinderService: RequirementFinderService,
): RequirementApi {
    private val logger = LoggerFactory.getLogger(InformationConceptEndpoint::class.java)

    @Bean
    override fun requirementGet(): RequirementGetFunction = f2Function { query ->
        logger.info("requirementGet: $query")
        val requirement = requirementFinderService.getOrNull(query.id)
        val graph = CccevFlatGraph().also { requirement?.flattenTo(it) }

        RequirementGetResult(
            item = graph.requirements[requirement?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun requirementGetByIdentifier(): RequirementGetByIdentifierFunction = f2Function { query ->
        logger.info("requirementGetByIdentifier: $query")
        val requirement = requirementFinderService.getOrNullByIdentifier(query.identifier)
        val graph = CccevFlatGraph().also { requirement?.flattenTo(it) }

        RequirementGetByIdentifierResult(
            item = graph.requirements[requirement?.identifier],
            graph = graph
        )
    }

    @Bean
    override fun requirementCreate(): RequirementCreateFunction = f2Function { command ->
        logger.info("requirementCreate: $command")
        requirementAggregateService.create(command)
    }

    @Bean
    override fun requirementUpdate(): RequirementUpdateFunction = f2Function { command ->
        logger.info("requirementCreate: $command")
        requirementAggregateService.update(command)
    }

    @Bean
    override fun requirementAddRequirements(): RequirementAddRequirementsFunction = f2Function { command ->
        logger.info("requirementAddRequirements: $command")
        requirementAggregateService.addRequirements(command)
    }

    @Bean
    override fun requirementRemoveRequirements(): RequirementRemoveRequirementsFunction = f2Function { command ->
        logger.info("requirementRemoveRequirements: $command")
        requirementAggregateService.removeRequirements(command)
    }

    @Bean
    override fun requirementAddConcepts(): RequirementAddConceptsFunction = f2Function { command ->
        logger.info("requirementAddConcepts: $command")
        requirementAggregateService.addConcepts(command)
    }

    @Bean
    override fun requirementRemoveConcepts(): RequirementRemoveConceptsFunction = f2Function { command ->
        logger.info("requirementRemoveConcepts: $command")
        requirementAggregateService.removeConcepts(command)
    }

    @Bean
    override fun requirementAddEvidenceTypes(): RequirementAddEvidenceTypesFunction = f2Function { command ->
        logger.info("requirementAddEvidenceTypes: $command")
        requirementAggregateService.addEvidenceTypes(command)
    }

    @Bean
    override fun requirementRemoveEvidenceTypes(): RequirementRemoveEvidenceTypesFunction = f2Function { command ->
        logger.info("requirementRemoveEvidenceTypes: $command")
        requirementAggregateService.removeEvidenceTypes(command)
    }
}

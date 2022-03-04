package cccev.f2.concept.api.app.service

import cccev.commons.exception.NotFoundException
import cccev.core.dsl.EvidenceTypeBase
import cccev.core.dsl.InformationConceptBase
import cccev.core.dsl.Requirement
import cccev.core.dsl.RequirementId
import cccev.f2.concept.api.app.model.toDTO
import cccev.f2.concept.api.domain.model.InformationConceptDTO
import cccev.f2.concept.api.domain.model.InformationConceptDTOBase
import cccev.s2.request.app.RequestAggregateService
import cccev.s2.request.app.RequestFinderService
import cccev.s2.request.domain.features.command.RequestInitCommand
import cccev.s2.request.domain.model.Request
import cccev.s2.request.domain.model.RequestId
import cccev.s2.requirement.app.RequirementFinderService
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class ConceptApiFinderService(
    private val requestAggregateService: RequestAggregateService,
    private val requestFinderService: RequestFinderService,
    private val requirementFinderService: RequirementFinderService
) {
    private val logger by Logger()

    suspend fun getInformationConcepts(requestId: RequestId, requirementId: RequirementId): List<InformationConceptDTO> {
        val requirement = requirementFinderService.get(requirementId)
            ?: throw NotFoundException("Requirement not found")

        try {
            RequestInitCommand(id = requestId, frameworkId = requirementId)
                .let { command -> requestAggregateService.init(command) }
                .let { event -> logger.info("Request [${event.id}] initialized") }
        } catch (e: DuplicateKeyException) {
            logger.info("Request exists")
        }

        val request = requestFinderService.get(requestId)!!
        return requirement.informationConcepts(request)
    }

    private fun Requirement.informationConcepts(request: Request): List<InformationConceptDTO> {
        return hasRequirement.orEmpty()
            .flatMap { it.informationConcepts(request) }
            .plus(hasConcept.orEmpty().toDTOs(this, request))
    }

    private fun List<InformationConceptBase>.toDTOs(parent: Requirement, request: Request): List<InformationConceptDTOBase> {
        val evidenceTypes = parent.hasEvidenceTypeList.orEmpty().map { etl ->
            etl.specifiesEvidenceType.map(EvidenceTypeBase::identifier)
        }
        return map { ic -> ic.toDTO(evidenceTypes, request.supportedValues[ic.identifier]) }
    }
}

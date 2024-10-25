package cccev.dsl.client.graph

import cccev.core.concept.model.InformationConceptId
import cccev.dsl.model.InformationConcept
import cccev.dsl.model.InformationConceptIdentifier
import cccev.f2.concept.client.InformationConceptClient
import cccev.f2.concept.domain.query.InformationConceptGetByIdentifierQueryDTOBase
import f2.dsl.fnc.invokeWith

class InformationConceptGraphInitializer(
    private val informationConceptClient: InformationConceptClient
): DependencyAwareGraphInitializer<InformationConcept, InformationConceptIdentifier, InformationConceptId>() {

    override fun getNodeReference(node: InformationConcept) = node.identifier
    override fun getNodeDependencies(node: InformationConcept) = node.dependsOn.orEmpty()

    override suspend fun tryLoadingExternalNode(nodeReference: InformationConceptIdentifier): InformationConceptId? {
        return InformationConceptGetByIdentifierQueryDTOBase(nodeReference)
            .invokeWith(informationConceptClient.conceptGetByIdentifier())
            .item
            ?.id
    }
}

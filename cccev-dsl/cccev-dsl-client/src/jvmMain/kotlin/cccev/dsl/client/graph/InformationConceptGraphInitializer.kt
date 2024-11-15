package cccev.dsl.client.graph

import cccev.client.InformationConceptClient
import cccev.dsl.model.InformationConceptDTO
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import cccev.f2.concept.query.InformationConceptGetByIdentifierQuery
import f2.dsl.fnc.invokeWith

class InformationConceptGraphInitializer(
    private val informationConceptClient: InformationConceptClient
): DependencyAwareGraphInitializer<InformationConceptDTO, InformationConceptIdentifier, InformationConceptId>() {

    override fun getNodeReference(node: InformationConceptDTO) = node.identifier
    override fun getNodeDependencies(node: InformationConceptDTO) = node.dependsOn.orEmpty()

    override suspend fun tryLoadingExternalNode(nodeReference: InformationConceptIdentifier): InformationConceptId? {
        return InformationConceptGetByIdentifierQuery(nodeReference)
            .invokeWith(informationConceptClient.conceptGetByIdentifier())
            .item
            ?.id
    }
}

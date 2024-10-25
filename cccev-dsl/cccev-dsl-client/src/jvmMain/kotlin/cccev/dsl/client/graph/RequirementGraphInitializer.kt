package cccev.dsl.client.graph

import cccev.core.requirement.model.RequirementId
import cccev.dsl.model.Requirement
import cccev.dsl.model.RequirementIdentifier
import cccev.f2.requirement.domain.query.RequirementGetByIdentifierQueryDTOBase
import cccev.s2.requirement.client.RequirementClient
import f2.dsl.fnc.invokeWith

class RequirementGraphInitializer(
    private val informationConceptClient: RequirementClient
): DependencyAwareGraphInitializer<Requirement, RequirementIdentifier, RequirementId>() {

    override fun getNodeReference(node: Requirement) = node.identifier
    override fun getNodeDependencies(node: Requirement): List<RequirementIdentifier> {
        val dependencies = buildList {
            node.hasRequirement?.let { addAll(it) }
            node.isRequirementOf?.let { addAll(it) }
        }
        return dependencies.map { it.identifier }.distinct()
    }

    override suspend fun tryLoadingExternalNode(nodeReference: RequirementIdentifier): RequirementId? {
        return RequirementGetByIdentifierQueryDTOBase(nodeReference)
            .invokeWith(informationConceptClient.requirementGetByIdentifier())
            .item
            ?.id
    }
}

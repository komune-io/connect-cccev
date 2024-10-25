package cccev.core.requirement.entity

import cccev.core.concept.entity.InformationConcept
import cccev.core.requirement.model.RequirementId
import cccev.core.requirement.model.RequirementIdentifier
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class RequirementRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: RequirementId): Requirement? = sessionFactory.session { session ->
        session.query(
            "MATCH (requirement:${Requirement.LABEL} {id: \$id})" +
                    "\nCALL apoc.path.subgraphAll(requirement, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN requirement, nodes, relationships",
            mapOf("id" to id)
        ).map { it["requirement"] as Requirement }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: RequirementIdentifier): Requirement? = sessionFactory.session { session ->
        session.query(
            "MATCH (requirement:${Requirement.LABEL} {identifier: \$identifier})" +
                    "\nCALL apoc.path.subgraphAll(requirement, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN requirement, nodes, relationships",
            mapOf("identifier" to identifier)
        ).map { it["requirement"] as Requirement }
            .firstOrNull()
    }

    suspend fun loadRequirementOnlyGraph(
        rootRequirementIdentifier: RequirementIdentifier
    ): Requirement? = sessionFactory.session { session ->
        val query = """
            MATCH (root:${Requirement.LABEL})
            -[has_requirement:${Requirement.HAS_REQUIREMENT}*0..]->(children:${Requirement.LABEL})
            WHERE root.${Requirement::identifier.name} = ${'$'}identifier
            RETURN root, collect(has_requirement), collect(children)
        """.trimIndent()

        session.query(query, mapOf("identifier" to rootRequirementIdentifier))
            .map { it["root"] as Requirement }
            .firstOrNull()
    }

    suspend fun hasAnyConcept(requirementIdentifier: RequirementIdentifier): Boolean = sessionFactory.session { session ->
        session.queryForObject(
            java.lang.Boolean::class.java,
            "RETURN EXISTS( " +
                    "(:${Requirement.LABEL} {identifier: \$identifier})" +
                    "-[:${Requirement.HAS_CONCEPT}]->(:${InformationConcept.LABEL})" +
                    ")",
            mapOf("identifier" to requirementIdentifier)
        ).booleanValue()
    }
}

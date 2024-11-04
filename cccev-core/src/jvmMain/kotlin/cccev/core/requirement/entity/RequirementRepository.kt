package cccev.core.requirement.entity

import cccev.core.concept.entity.InformationConceptEntity
import cccev.dsl.model.RequirementId
import cccev.dsl.model.RequirementIdentifier
import cccev.infra.neo4j.returnWholeEntity
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class RequirementRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: RequirementId): RequirementEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (requirement:${RequirementEntity.LABEL} {id: \$id})"
                .returnWholeEntity("requirement"),
            mapOf("id" to id)
        ).map { it["requirement"] as RequirementEntity }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: RequirementIdentifier): RequirementEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (requirement:${RequirementEntity.LABEL} {identifier: \$identifier})"
                .returnWholeEntity("requirement"),
            mapOf("identifier" to identifier)
        ).map { it["requirement"] as RequirementEntity }
            .firstOrNull()
    }

    suspend fun loadRequirementOnlyGraph(
        rootRequirementIdentifier: RequirementIdentifier
    ): RequirementEntity? = sessionFactory.session { session ->
        val query = """
            MATCH (root:${RequirementEntity.LABEL})
            -[has_requirement:${RequirementEntity.HAS_REQUIREMENT}*0..]->(children:${RequirementEntity.LABEL})
            WHERE root.${RequirementEntity::identifier.name} = ${'$'}identifier
            RETURN root, collect(has_requirement), collect(children)
        """.trimIndent()

        session.query(query, mapOf("identifier" to rootRequirementIdentifier))
            .map { it["root"] as RequirementEntity }
            .firstOrNull()
    }

    suspend fun hasAnyConcept(requirementIdentifier: RequirementIdentifier): Boolean = sessionFactory.session { session ->
        session.queryForObject(
            java.lang.Boolean::class.java,
            "RETURN EXISTS( " +
                    "(:${RequirementEntity.LABEL} {identifier: \$identifier})" +
                    "-[:${RequirementEntity.HAS_CONCEPT}]->(:${InformationConceptEntity.LABEL})" +
                    ")",
            mapOf("identifier" to requirementIdentifier)
        ).booleanValue()
    }
}

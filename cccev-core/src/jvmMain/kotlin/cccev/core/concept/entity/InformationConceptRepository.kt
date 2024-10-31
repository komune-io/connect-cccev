package cccev.core.concept.entity

import cccev.core.unit.entity.DataUnitEntity
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import cccev.infra.neo4j.returnWholeEntity
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class InformationConceptRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: InformationConceptId): InformationConceptEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (ic:${InformationConceptEntity.LABEL} {id: \$id})"
                .returnWholeEntity("ic"),
            mapOf("id" to id)
        ).map { it["ic"] as InformationConceptEntity }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: InformationConceptIdentifier): InformationConceptEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (ic:${InformationConceptEntity.LABEL} {identifier: \$identifier})"
                .returnWholeEntity("ic"),
            mapOf("identifier" to identifier)
        ).map {
            it["ic"] as InformationConceptEntity
        }.firstOrNull()
    }

    suspend fun findShallowByIdentifier(identifier: InformationConceptIdentifier): InformationConceptEntity? = sessionFactory.session { session ->
        session.queryForObject(
            InformationConceptEntity::class.java,
            "MATCH (ic:${InformationConceptEntity.LABEL} {identifier: \$identifier})" +
                    "\nRETURN ic",
            mapOf("identifier" to identifier)
        )
    }

    suspend fun findDependingOn(
        identifier: InformationConceptIdentifier
    ): List<InformationConceptEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (ic:${InformationConceptEntity.LABEL})" +
                    "-[:${InformationConceptEntity.DEPENDS_ON}]->(:${InformationConceptEntity.LABEL} {identifier: \$identifier})" +
                    "\nMATCH (ic:${InformationConceptEntity.LABEL})" +
                    "-[depends_on:${InformationConceptEntity.DEPENDS_ON}]->(dep:${InformationConceptEntity.LABEL})" +
                    "MATCH (ic)-[has_unit:${InformationConceptEntity.HAS_UNIT}]->(du:${DataUnitEntity.LABEL})\n" +
                    "\nRETURN ic, collect(depends_on), collect(dep), collect(has_unit), collect(du)",
            mapOf("identifier" to identifier)
        ).map { it["ic"] as InformationConceptEntity }
    }
}

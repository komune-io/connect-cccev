package cccev.core.unit.entity

import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitIdentifier
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class DataUnitRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: DataUnitId): DataUnit? = sessionFactory.session { session ->
        session.query(
            "MATCH (unit:${DataUnit.LABEL} {id: \$id})" +
                    "\nCALL apoc.path.subgraphAll(unit, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN unit, nodes, relationships",
            mapOf("id" to id)
        ).map { it["unit"] as DataUnit }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: DataUnitIdentifier): DataUnit? = sessionFactory.session { session ->
        session.query(
            "MATCH (unit:${DataUnit.LABEL} {identifier: \$identifier})" +
                    "\nCALL apoc.path.subgraphAll(unit, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN unit, nodes, relationships",
            mapOf("identifier" to identifier)
        ).map { it["unit"] as DataUnit }
            .firstOrNull()
    }
}

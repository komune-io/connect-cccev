package cccev.core.unit.entity

import cccev.dsl.model.DataUnitId
import cccev.dsl.model.DataUnitIdentifier
import cccev.infra.neo4j.returnWholeEntity
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class DataUnitRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: DataUnitId): DataUnitEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (unit:${DataUnitEntity.LABEL} {id: \$id})"
                .returnWholeEntity("unit"),
            mapOf("id" to id)
        ).map { it["unit"] as DataUnitEntity }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: DataUnitIdentifier): DataUnitEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (unit:${DataUnitEntity.LABEL} {identifier: \$identifier})"
                .returnWholeEntity("unit"),
            mapOf("identifier" to identifier)
        ).map { it["unit"] as DataUnitEntity }
            .firstOrNull()
    }
}

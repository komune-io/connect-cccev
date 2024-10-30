package cccev.core.evidencetype.entity

import cccev.infra.neo4j.returnWholeEntity
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class EvidenceTypeRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: String): EvidenceType? = sessionFactory.session { session ->
        session.query(
            "MATCH (et:${EvidenceType.LABEL} {id: \$id})"
                .returnWholeEntity("et"),
            mapOf("id" to id)
        ).map { it["et"] as EvidenceType }
            .firstOrNull()
    }

    suspend fun findByIdentifier(identifier: String): EvidenceType? = sessionFactory.session { session ->
        session.queryForObject(
            EvidenceType::class.java,
            "MATCH (c:${EvidenceType.LABEL} {identifier: \$identifier})" +
                    "\nCALL apoc.path.subgraphAll(c, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN nodes, relationships",
            mapOf("identifier" to identifier)
        )
    }
}

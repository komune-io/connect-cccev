package cccev.core.evidencetype.entity

import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class EvidenceTypeRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: String): EvidenceType? = sessionFactory.session { session ->
        session.queryForObject(
            EvidenceType::class.java,
            "MATCH (c:${EvidenceType.LABEL} {id: \$id})" +
                    "\nCALL apoc.path.subgraphAll(c, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN nodes, relationships",
            mapOf("id" to id)
        )
    }
}

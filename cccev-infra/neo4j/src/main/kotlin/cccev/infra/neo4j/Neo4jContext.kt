package cccev.infra.neo4j

import kotlin.coroutines.CoroutineContext
import org.neo4j.ogm.session.Session

data class Neo4jContext(
    val session: Session
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    companion object Key: CoroutineContext.Key<Neo4jContext>
}

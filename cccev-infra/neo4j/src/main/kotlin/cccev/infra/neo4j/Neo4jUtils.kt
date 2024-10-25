package cccev.infra.neo4j

import cccev.commons.utils.mapAsync
import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.transaction.Transaction
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> SessionFactory.session(additionalContext: CoroutineContext = EmptyCoroutineContext, execute: suspend (Session) -> T): T {
    val session = coroutineContext[Neo4jContext]?.session
        ?: return withContext(
            Dispatchers.IO.limitedParallelism(1) // must stay on the same thread for transactions to work
                    + Neo4jContext(openSession())
                    + additionalContext
        ) { session(additionalContext, execute) }

    return execute(session)
}

suspend fun <T> SessionFactory.transaction(
    additionalContext: CoroutineContext = EmptyCoroutineContext,
    execute: suspend (Session, Transaction) -> T
): T {
    return session(additionalContext) { session ->
        val existingTx = session.transaction
            ?.takeIf { it.status() == Transaction.Status.OPEN }

        if (existingTx != null) {
            execute(session, existingTx)
        } else {
            session.beginTransaction().use { tx ->
                try {
                    val result = execute(session, tx)
                    tx.commit()
                    result
                } catch (e: Exception) {
                    tx.rollback()
                    throw e
                }
            }
        }
    }
}

inline fun <reified E: Any> Session.checkNotExists(id: String, label: String) {
    val existingEntity = load(E::class.java, id, 0)
    if (existingEntity != null) {
        throw ConflictException(label, "id", id)
    }
}

inline fun <reified E: Any> Session.findSafeShallowById(id: String, label: String): E {
    return load(E::class.java, id, 0)
        ?: throw NotFoundException(label, id)
}

suspend inline fun <reified E: Any> Session.findSafeShallowAllById(ids: Collection<String>, label: String) = ids.mapAsync { id ->
    load(E::class.java, id, 0)
        ?: throw NotFoundException(label, id)
}

fun Session.removeRelation(originLabel: String, originId: String, relationLabel: String, targetLabel: String, targetId: String) {
    val query = """
        MATCH (origin:$originLabel {id: ${'$'}originId})-[relation: $relationLabel]->(target:$targetLabel {id: ${'$'}targetId})
        DELETE relation
    """.trimIndent()

    query(query, mapOf("originId" to originId, "targetId" to targetId))
}

suspend fun Session.removeRelations(
    originLabel: String,
    originId: String,
    relationLabel: String,
    targetLabel: String,
    targetIds: Collection<String>
) {
    targetIds.mapAsync { targetId ->
        removeRelation(originLabel, originId, relationLabel, targetLabel, targetId)
    }
}

suspend fun Session.removeSeveredRelations(
    originLabel: String,
    originId: String,
    relationLabel: String,
    targetLabel: String,
    currentTargetIds: Collection<String>,
    newTargetIds: Set<String>
) {
    removeRelations(
        originLabel,
        originId,
        relationLabel,
        targetLabel,
        currentTargetIds.filter { it !in newTargetIds }
    )
}

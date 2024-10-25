package cccev.test

import cccev.infra.neo4j.session
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.neo4j.ogm.session.SessionFactory
import s2.bdd.data.TestContext

class EnvironmentCleanerSteps(
    private val context: TestContext,
    private val sessionFactory: SessionFactory
): En {
    init {
        Before { _ ->
//            cleanFs()
            context.reset()
            cleanDb()
        }
    }

    // TODO activate when fs added
//    private fun cleanFs() = runBlocking {
//        context.filePaths.items
//            .ifEmpty { return@runBlocking  }
//            .let { fileClient.fileGet(it) }
//            .mapNotNull { it.file?.path }
//            .let { fileClient.fileDelete(it) }
//    }

    private fun cleanDb() = runBlocking {
        sessionFactory.session { session ->
            session.query("MATCH (n) DETACH DELETE n", emptyMap<String, Any>())
        }
    }
}

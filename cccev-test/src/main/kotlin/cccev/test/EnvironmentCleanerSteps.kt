package cccev.test

import fixers.bdd.data.TestContext
import io.cucumber.java8.En
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runBlocking
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.data.neo4j.core.PreparedQuery
import org.springframework.data.neo4j.core.ReactiveNeo4jClient
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.data.neo4j.core.await
import org.springframework.data.neo4j.core.deleteAll
import org.springframework.data.neo4j.core.inDatabase

class EnvironmentCleanerSteps(
    private val context: TestContext,
    private val mongoTemplate: ReactiveMongoTemplate,
    private val neo4jTemplate: Neo4jClient
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
        mongoTemplate.collectionNames.map(mongoTemplate::dropCollection).collectList()
        neo4jTemplate.query("MATCH ()-[r]-() DELETE r").run()
        neo4jTemplate.query("MATCH (n) DELETE n").run()
    }
}

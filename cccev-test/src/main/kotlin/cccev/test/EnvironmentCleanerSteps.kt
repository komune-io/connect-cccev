package cccev.test

import cccev.infra.neo4j.session
import io.cucumber.java8.En
import io.komune.fs.s2.file.client.FileClient
import io.komune.fs.s2.file.domain.features.command.FileDeleteCommand
import io.komune.fs.s2.file.domain.features.query.FileListQuery
import kotlinx.coroutines.runBlocking
import org.neo4j.ogm.session.SessionFactory
import s2.bdd.data.TestContext

class EnvironmentCleanerSteps(
    private val context: TestContext,
    private val fileClient: FileClient,
    private val sessionFactory: SessionFactory
): En {
    init {
        Before { _ ->
            context.reset()
            cleanDb()
            cleanFs()
        }
    }

    private fun cleanFs() = runBlocking {
        fileClient.fileList(listOf(FileListQuery(
            objectType = null,
            objectId = null,
            directory = null,
            recursive = true
        ))).first().items.forEach {
            fileClient.fileDelete(listOf(
                FileDeleteCommand(
                    objectType = it.path.objectType,
                    objectId = it.path.objectId,
                    directory = it.path.directory,
                    name = it.path.name
                )
            ))
        }
    }

    private fun cleanDb() = runBlocking {
        sessionFactory.session { session ->
            session.query("MATCH (n) DETACH DELETE n", emptyMap<String, Any>())
        }
    }
}

package cccev.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.neo4j.config.EnableReactiveNeo4jAuditing
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories

@EntityScan("cccev")
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cccev")
@EnableReactiveNeo4jAuditing
@EnableReactiveNeo4jRepositories("cccev")
@SpringBootApplication(scanBasePackages = ["cccev"])
class CccevApplication

fun main(args: Array<String>) {
	runApplication<CccevApplication>(*args)
}

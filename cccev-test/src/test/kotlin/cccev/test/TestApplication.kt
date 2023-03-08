package cccev.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EntityScan("cccev")
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cccev")
@EnableReactiveNeo4jRepositories("cccev")
@SpringBootApplication(scanBasePackages = ["cccev", "fixers.bdd"])
class TestApplication

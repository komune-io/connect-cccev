package cccev.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EntityScan("cccev")
@SpringBootApplication(scanBasePackages = ["cccev", "s2.bdd"])
class TestApplication

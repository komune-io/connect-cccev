package cccev.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan("cccev")
@SpringBootApplication(scanBasePackages = ["cccev"])
class CccevApplication

fun main(args: Array<String>) {
	runApplication<CccevApplication>(*args)
}

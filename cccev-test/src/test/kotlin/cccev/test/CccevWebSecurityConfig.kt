package cccev.test;

import io.komune.i2.spring.boot.auth.config.WebSecurityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@Configuration
@EnableWebFluxSecurity
@SpringBootApplication(exclude=[SecurityAutoConfiguration::class])
class CccevWebSecurityConfig: WebSecurityConfig()

package ro.daniil.zaru.phonebookingsystem.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfig(private val env: Environment) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        val url = env.getRequiredProperty("spring.flyway.url")
        val username = env.getRequiredProperty("spring.flyway.username")
        val password = env.getRequiredProperty("spring.flyway.password")
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                    url,
                    username,
                    password
                )
        )
    }
}
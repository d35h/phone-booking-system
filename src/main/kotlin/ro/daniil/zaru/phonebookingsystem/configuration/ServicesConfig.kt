package ro.daniil.zaru.phonebookingsystem.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ro.daniil.zaru.phonebookingsystem.repositroy.PhoneIdBookedByRepository
import ro.daniil.zaru.phonebookingsystem.repositroy.PhoneRepository
import ro.daniil.zaru.phonebookingsystem.service.PhoneIdByBookedByService
import ro.daniil.zaru.phonebookingsystem.service.PhoneService

@Configuration
class ServicesConfig {
    @Bean
    fun phoneService(repo: PhoneRepository) = PhoneService(repo)

    @Bean
    fun phoneIdByBookedByService(repo: PhoneIdBookedByRepository) = PhoneIdByBookedByService(repo)
}
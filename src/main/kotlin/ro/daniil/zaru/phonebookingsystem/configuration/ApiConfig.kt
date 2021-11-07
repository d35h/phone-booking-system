package ro.daniil.zaru.phonebookingsystem.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ro.daniil.zaru.phonebookingsystem.api.PhonesController
import ro.daniil.zaru.phonebookingsystem.service.PhoneIdByBookedByService
import ro.daniil.zaru.phonebookingsystem.service.PhoneService

@Configuration
class ApiConfig {
    @Bean
    fun phonesController(
        phoneService: PhoneService,
        phoneIdByBookedByService: PhoneIdByBookedByService
    ): PhonesController = PhonesController(phoneService, phoneIdByBookedByService)
}
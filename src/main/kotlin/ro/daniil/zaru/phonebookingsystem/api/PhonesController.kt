package ro.daniil.zaru.phonebookingsystem.api

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ro.daniil.zaru.phonebookingsystem.model.PhoneDto
import ro.daniil.zaru.phonebookingsystem.model.PhoneIdToBookedBy
import ro.daniil.zaru.phonebookingsystem.service.PhoneIdByBookedByService
import ro.daniil.zaru.phonebookingsystem.service.PhoneService
import java.time.Instant

@OptIn(FlowPreview::class)
class PhonesController(
    private val phoneService: PhoneService,
    private val phoneIdByBookedByService: PhoneIdByBookedByService
) : PhoneApiDelegate {

    override fun unbook(id: Long, exchange: ServerWebExchange): Mono<ResponseEntity<Void>> = mono {
        val foundPhone = phoneService.getById(id)
        if (foundPhone == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            val toUnbook = phoneIdByBookedByService.findById(id) ?: return@mono ResponseEntity(HttpStatus.BAD_REQUEST)

            phoneIdByBookedByService.unbook(toUnbook)
            ResponseEntity(HttpStatus.OK)
        }
    }

    override fun book(id: Long, bookedBy: String, exchange: ServerWebExchange?): Mono<ResponseEntity<Void>> = mono {
        val foundPhone = phoneService.getById(id)
        if (foundPhone == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            if (phoneIdByBookedByService.findById(id) != null) {
                return@mono ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            phoneIdByBookedByService.book(PhoneIdToBookedBy(foundPhone.id, bookedBy, Instant.now()))
            ResponseEntity(HttpStatus.OK)
        }
    }

    override fun getByManufacturerAndModel(
        manufacturer: String,
        model: String,
        exchange: ServerWebExchange?
    ): Mono<ResponseEntity<PhoneDto>> = mono {
        val foundPhone = phoneService.getByManufacturerAndModel(manufacturer, model)
        if (foundPhone == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            val phoneIdToBookedBy: PhoneIdToBookedBy? = phoneIdByBookedByService.findById(foundPhone.id)
            val phoneDto = foundPhone.toDto(phoneIdToBookedBy?.bookedBy, phoneIdToBookedBy?.bookedTime)
            ResponseEntity(phoneDto, HttpStatus.OK)
        }
    }

    override fun getAll(exchange: ServerWebExchange?): Mono<ResponseEntity<Flux<PhoneDto>>> = mono {
        val storedPhones = phoneService.getAll()
        val phoneIdToBookedBy: Flow<PhoneIdToBookedBy> = phoneIdByBookedByService
            .findAllById(storedPhones.map { it.id })

        val phonesDto = storedPhones.map { phone ->
            val pidtb = phoneIdToBookedBy.firstOrNull { phone.id == it.phoneId }
            phone.toDto(pidtb?.bookedBy, pidtb?.bookedTime)
        }.asFlux()

        ResponseEntity(phonesDto, HttpStatus.OK)
    }
}
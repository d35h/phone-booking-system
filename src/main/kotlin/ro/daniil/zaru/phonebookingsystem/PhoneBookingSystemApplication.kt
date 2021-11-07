package ro.daniil.zaru.phonebookingsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhoneBookingSystemApplication

fun main(args: Array<String>) {
	runApplication<PhoneBookingSystemApplication>(*args)
}

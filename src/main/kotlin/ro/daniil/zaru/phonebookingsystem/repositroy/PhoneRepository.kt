package ro.daniil.zaru.phonebookingsystem.repositroy

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ro.daniil.zaru.phonebookingsystem.model.Phone

@Repository
interface PhoneRepository: CoroutineCrudRepository<Phone, Long> {
    suspend fun findPhoneByManufacturerAndModel(manufacturer: String, model: String): Phone?
    suspend fun getById(id: Long): Phone?
}
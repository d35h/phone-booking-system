package ro.daniil.zaru.phonebookingsystem.service

import ro.daniil.zaru.phonebookingsystem.repositroy.PhoneRepository

class PhoneService(private val repo: PhoneRepository) {
    suspend fun getById(id: Long) = repo.findById(id)
    fun getAll() = repo.findAll()
    suspend fun getByManufacturerAndModel(manufacturer: String, model: String) =
        repo.findPhoneByManufacturerAndModel(manufacturer, model)
}
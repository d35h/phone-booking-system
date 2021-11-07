package ro.daniil.zaru.phonebookingsystem.service

import kotlinx.coroutines.flow.Flow
import ro.daniil.zaru.phonebookingsystem.model.PhoneIdToBookedBy
import ro.daniil.zaru.phonebookingsystem.repositroy.PhoneIdBookedByRepository

class PhoneIdByBookedByService(private val repository: PhoneIdBookedByRepository) {
    fun findAllById(ids: Flow<Long>): Flow<PhoneIdToBookedBy> = repository.findAllById(ids)
    suspend fun findById(id: Long): PhoneIdToBookedBy? = repository.findById(id)
    suspend fun book(toSave: PhoneIdToBookedBy) = repository.save(toSave)
    suspend fun unbook(toSave: PhoneIdToBookedBy) = repository.delete(toSave)
}
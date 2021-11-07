package ro.daniil.zaru.phonebookingsystem.repositroy

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ro.daniil.zaru.phonebookingsystem.model.PhoneIdToBookedBy

@Repository
interface PhoneIdBookedByRepository: CoroutineCrudRepository<PhoneIdToBookedBy, Long>
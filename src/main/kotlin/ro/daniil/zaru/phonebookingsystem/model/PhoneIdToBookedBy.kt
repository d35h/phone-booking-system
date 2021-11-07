package ro.daniil.zaru.phonebookingsystem.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table
data class PhoneIdToBookedBy(
    @Id
    val phoneId: Long,
    val bookedBy: String,
    val bookedTime: Instant,
) : Persistable<Long> {
    @Transient
    var isUpdate = false
    override fun getId() = phoneId
    override fun isNew() = !isUpdate
}
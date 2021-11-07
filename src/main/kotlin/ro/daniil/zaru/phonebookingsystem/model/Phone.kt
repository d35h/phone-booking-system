package ro.daniil.zaru.phonebookingsystem.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.time.ZoneOffset

@Table
data class Phone(
    @Id
    val id: Long,
    val manufacturer: String,
    val model: String,
    val technology: String,
    val bands: String,
) {
    fun toDto(bookedBy: String?, bookedTime: Instant?): PhoneDto {
        val domain = this
        return PhoneDto().apply {
            description = "$manufacturer $model"
            this.isBooked = !bookedBy.isNullOrEmpty()
            this.id = domain.id
            this.bookedTime = bookedTime?.atOffset(ZoneOffset.UTC)
            this.technology = domain.technology
            this.bookedBy = bookedBy
            this.bands = domain.bands.split(",").map { bandToFrequency ->
                BandDto().apply {
                    val (b, f) = bandToFrequency.split(":")
                    band = b
                    frequency = f
                }
            }
        }
    }
}
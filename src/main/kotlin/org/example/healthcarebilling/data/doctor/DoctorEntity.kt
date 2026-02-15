package org.example.healthcarebilling.data.doctor

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.healthcarebilling.domain.doctor.Doctor
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "doctor")
data class DoctorEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val npiNumber: String,
    val specialty: String,
    val practiceStartDate: LocalDate
) {
    fun toDomain(): Doctor {
        return Doctor(
            id = id,
            firstName = firstName,
            lastName = lastName,
            npiNumber = npiNumber,
            specialty = specialty,
            practiceStartDate = practiceStartDate
        )
    }

    companion object {
        fun fromDomain(doctor: Doctor): DoctorEntity {
            return DoctorEntity(
                id = doctor.id,
                firstName = doctor.firstName,
                lastName = doctor.lastName,
                npiNumber = doctor.npiNumber,
                specialty = doctor.specialty,
                practiceStartDate = doctor.practiceStartDate
            )
        }
    }
}


package org.example.healthcarebilling.data.patient

import jakarta.persistence.*
import org.example.healthcarebilling.domain.patient.Patient
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "patient")
data class PatientEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "insurance_id")
    val insurance: InsuranceEntity? = null
) {
    fun toDomain(): Patient {
        return Patient(
            id = id,
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dateOfBirth,
            insurance = insurance?.toDomain()
        )
    }

    companion object {
        fun fromDomain(patient: Patient): PatientEntity {
            return PatientEntity(
                id = patient.id,
                firstName = patient.firstName,
                lastName = patient.lastName,
                dateOfBirth = patient.dateOfBirth,
                insurance = patient.insurance?.let { InsuranceEntity.fromDomain(it) }
            )
        }
    }
}


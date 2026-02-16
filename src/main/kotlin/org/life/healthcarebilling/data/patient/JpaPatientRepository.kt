package org.life.healthcarebilling.data.patient

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface JpaPatientRepository : JpaRepository<PatientEntity, UUID> {
    fun findByFirstNameAndLastNameAndDateOfBirth(
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate
    ): PatientEntity?
}


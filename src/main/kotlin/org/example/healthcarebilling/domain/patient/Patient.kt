package org.example.healthcarebilling.domain.patient

import java.time.LocalDate
import java.time.Period

data class Patient(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
) {
    val age: Int get() = Period.between(dateOfBirth, LocalDate.now()).years
}

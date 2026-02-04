package org.example.healthcarebilling.domain.patient

import java.time.LocalDate

data class Patient(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
) {
}

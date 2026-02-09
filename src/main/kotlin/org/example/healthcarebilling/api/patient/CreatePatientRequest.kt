package org.example.healthcarebilling.api.patient

import java.time.LocalDate

data class CreatePatientRequest(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
)

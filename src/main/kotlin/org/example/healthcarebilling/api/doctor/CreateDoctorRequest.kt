package org.example.healthcarebilling.api.doctor

import java.time.LocalDate

data class CreateDoctorRequest(
    val firstName: String,
    val lastName: String,
    val npiNumber: String,
    val specialty: String,
    val practiceStartDate: LocalDate
)

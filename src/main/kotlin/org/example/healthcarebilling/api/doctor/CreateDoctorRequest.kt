package org.example.healthcarebilling.api.doctor

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class CreateDoctorRequest(
    @NotBlank(message = "First Name cannot be blank")
    val firstName: String,
    @NotBlank(message = "Last Name cannot be blank")
    val lastName: String,
    @NotBlank(message = "Npi Number cannot be blank")
    val npiNumber: String,
    val specialty: String,
    @Past(message = "Practice start date must be in the past")
    val practiceStartDate: LocalDate
)

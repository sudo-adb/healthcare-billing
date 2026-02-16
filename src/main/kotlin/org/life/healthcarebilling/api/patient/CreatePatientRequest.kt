package org.life.healthcarebilling.api.patient

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class CreatePatientRequest(
    @NotBlank(message = "First Name cannot be blank")
    val firstName: String,
    @NotBlank(message = "Last Name cannot be blank")
    val lastName: String,
    @Past(message = "Date of birth must be in the past")
    val dateOfBirth: LocalDate
)

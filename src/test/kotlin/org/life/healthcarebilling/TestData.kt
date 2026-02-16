package org.life.healthcarebilling

import org.life.healthcarebilling.domain.doctor.Doctor
import org.life.healthcarebilling.domain.patient.Patient
import java.time.LocalDate

val patient1 = Patient(firstName = "John", lastName = "Doe", dateOfBirth = LocalDate.of(1990, 6, 15))
val patient2 = Patient(firstName = "Bruce", lastName = "Wayne", dateOfBirth = LocalDate.of(1990, 6, 15))

val doctor = Doctor(
    firstName = "Jane",
    lastName = "Smith",
    npiNumber = "1234567890",
    specialty = "Cardio",
    practiceStartDate = LocalDate.of(2015, 6, 15)
)

val mediumExpCardioDoctor = Doctor(
    firstName = "Jane",
    lastName = "Smith",
    npiNumber = "1234567890",
    specialty = "Cardio",
    practiceStartDate = LocalDate.of(2000, 6, 15)
)

val highExpOrthoDoctor = Doctor(
    firstName = "Shaun",
    lastName = "Murphy",
    npiNumber = "1234567890",
    specialty = "Ortho",
    practiceStartDate = LocalDate.of(1980, 6, 15)
)

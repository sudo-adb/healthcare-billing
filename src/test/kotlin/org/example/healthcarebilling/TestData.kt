package org.example.healthcarebilling

import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.patient.Patient
import java.time.LocalDate

val patient1 = Patient(firstName = "John", lastName = "Doe", dateOfBirth = LocalDate.of(1990, 6, 15))
val patient2 = Patient(firstName = "Bruce", lastName = "Wayne", dateOfBirth = LocalDate.of(1990, 6, 15))

val doctor = Doctor(
    firstName = "Jane",
    lastName = "Smith",
    npiNumber = "1234567890",
    specialty = "Cardiology",
    practiceStartDate = LocalDate.of(2015, 6, 15)
)

package org.example.healthcarebilling.domain.patient

import java.time.LocalDate

class CreatePatientUseCase(val patientRepository: PatientRepository) {

    operator fun invoke(firstName: String, lastName: String, dateOfBirth: String): Patient {
        val patient = Patient(firstName, lastName, LocalDate.parse(dateOfBirth))
        return patientRepository.save(patient)
    }
}

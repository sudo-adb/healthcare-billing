package org.example.healthcarebilling.domain.patient

class CreatePatientUseCase(val patientRepository: PatientRepository) {

    operator fun invoke(firstName: String, lastName: String, dateOfBirth: String): Patient {
        val patient = Patient(firstName, lastName, dateOfBirth)
        return patientRepository.save(patient)
    }
}

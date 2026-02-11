package org.example.healthcarebilling.domain.patient

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GetPatientUseCase(val patientRepository: PatientRepository) {

    operator fun invoke(firstName: String, lastName: String, dateOfBirth: LocalDate): Patient? {
        return patientRepository.findByFirstNameLastNameAndDateOfBirth(firstName, lastName, dateOfBirth)
    }
}

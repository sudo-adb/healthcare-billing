package org.example.healthcarebilling.domain.doctor

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CreateDoctorUseCase(val doctorRepository: DoctorRepository) {

    operator fun invoke(
        firstName: String,
        lastName: String,
        npiNumber: String,
        specialty: String,
        practiceStartDate: String
    ): Doctor {
        val doctor = Doctor(
            firstName,
            lastName,
            npiNumber,
            specialty,
            LocalDate.parse(practiceStartDate)
        )
        return doctorRepository.save(doctor)
    }
}

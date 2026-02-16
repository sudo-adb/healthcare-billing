package org.life.healthcarebilling.domain.doctor

import org.springframework.stereotype.Component

@Component
class GetDoctorByNpiNumberUseCase(private val doctorRepository: DoctorRepository) {

    operator fun invoke(npiNumber: String): Doctor? {
        return doctorRepository.findByNpiNumber(npiNumber)
    }
}


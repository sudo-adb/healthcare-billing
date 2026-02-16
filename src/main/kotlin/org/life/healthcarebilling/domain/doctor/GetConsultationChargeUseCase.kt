package org.life.healthcarebilling.domain.doctor

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class GetConsultationChargeUseCase(
    private val consultationChargeRepository: ConsultationChargeRepository
) {

    operator fun invoke(specialty: String, yearsOfExperience: Int): BigDecimal {
        val consultationCharge = consultationChargeRepository.findBySpecialtyAndExperience(specialty, yearsOfExperience)
        return consultationCharge?.amount ?: throw IllegalArgumentException("No consultation charge found for specialty: $specialty with $yearsOfExperience years of experience")
    }
}

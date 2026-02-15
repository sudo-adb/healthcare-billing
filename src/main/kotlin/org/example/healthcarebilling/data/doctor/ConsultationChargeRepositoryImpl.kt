package org.example.healthcarebilling.data.doctor

import org.example.healthcarebilling.domain.doctor.ConsultationCharge
import org.example.healthcarebilling.domain.doctor.ConsultationChargeRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
class ConsultationChargeRepositoryImpl(
    private val jpaConsultationChargeRepository: JpaConsultationChargeRepository
) : ConsultationChargeRepository {

    override fun findBySpecialtyAndExperience(specialty: String, yearsOfExperience: Int): ConsultationCharge? {
        val exactMatches = jpaConsultationChargeRepository.findBySpecialtyAndExperience(specialty, yearsOfExperience)
        if (exactMatches.isNotEmpty()) {
            return exactMatches.first().toDomain()
        }

        val generalMatches = jpaConsultationChargeRepository.findBySpecialtyAndExperience("GENERAL", yearsOfExperience)
        return generalMatches.firstOrNull()?.toDomain()
    }
}


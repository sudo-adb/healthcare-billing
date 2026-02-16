package org.life.healthcarebilling.domain.doctor

interface ConsultationChargeRepository {
    fun findBySpecialtyAndExperience(specialty: String, yearsOfExperience: Int): ConsultationCharge?
}

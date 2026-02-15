package org.example.healthcarebilling.data.doctor

import org.example.healthcarebilling.domain.doctor.ConsultationCharge
import org.example.healthcarebilling.domain.doctor.ConsultationChargeRepository
import java.math.BigDecimal

class InMemoryConsultationChargeRepository : ConsultationChargeRepository {

    private val charges = mutableListOf<ConsultationCharge>()

    init {

        //Ortho
        charges.add(ConsultationCharge("ORTHO", 0, 19, BigDecimal("800.00")))
        charges.add(ConsultationCharge("ORTHO", 20, 30, BigDecimal("1000.00")))
        charges.add(ConsultationCharge("ORTHO", 31, null, BigDecimal("1200.00")))

        // Cardio
        charges.add(ConsultationCharge("CARDIO", 0, 19, BigDecimal("1000.00")))
        charges.add(ConsultationCharge("CARDIO", 20, 30, BigDecimal("1500.00")))
        charges.add(ConsultationCharge("CARDIO", 31, null, BigDecimal("2000.00")))

        // General
        charges.add(ConsultationCharge("GENERAL", 0, 19, BigDecimal("500.00")))
        charges.add(ConsultationCharge("GENERAL", 20, 30, BigDecimal("600.00")))
        charges.add(ConsultationCharge("GENERAL", 31, null, BigDecimal("700.00")))
    }

    override fun findBySpecialtyAndExperience(specialty: String, yearsOfExperience: Int): ConsultationCharge? {
        val exactMatch = charges.firstOrNull { it.isApplicable(specialty, yearsOfExperience) }
        if (exactMatch != null) {
            return exactMatch
        }


        return charges.firstOrNull { it.specialty == "GENERAL" && it.isApplicable("GENERAL", yearsOfExperience) }
    }
}


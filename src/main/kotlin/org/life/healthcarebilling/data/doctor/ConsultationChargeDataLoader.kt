package org.life.healthcarebilling.data.doctor

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

//Initialize consultation charge data into the database when the application starts.
@Component
class ConsultationChargeDataLoader(
    private val jpaConsultationChargeRepository: JpaConsultationChargeRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (jpaConsultationChargeRepository.count() == 0L) {
            val charges = listOf(
                // Ortho
                ConsultationChargeEntity(specialty = "ORTHO", minYearsExperience = 0, maxYearsExperience = 19, amount = BigDecimal("800.00")),
                ConsultationChargeEntity(specialty = "ORTHO", minYearsExperience = 20, maxYearsExperience = 30, amount = BigDecimal("1000.00")),
                ConsultationChargeEntity(specialty = "ORTHO", minYearsExperience = 31, maxYearsExperience = null, amount = BigDecimal("1200.00")),

                // Cardio
                ConsultationChargeEntity(specialty = "CARDIO", minYearsExperience = 0, maxYearsExperience = 19, amount = BigDecimal("1000.00")),
                ConsultationChargeEntity(specialty = "CARDIO", minYearsExperience = 20, maxYearsExperience = 30, amount = BigDecimal("1500.00")),
                ConsultationChargeEntity(specialty = "CARDIO", minYearsExperience = 31, maxYearsExperience = null, amount = BigDecimal("2000.00")),

                // General
                ConsultationChargeEntity(specialty = "GENERAL", minYearsExperience = 0, maxYearsExperience = 19, amount = BigDecimal("500.00")),
                ConsultationChargeEntity(specialty = "GENERAL", minYearsExperience = 20, maxYearsExperience = 30, amount = BigDecimal("600.00")),
                ConsultationChargeEntity(specialty = "GENERAL", minYearsExperience = 31, maxYearsExperience = null, amount = BigDecimal("700.00"))
            )

            jpaConsultationChargeRepository.saveAll(charges)
        }
    }
}



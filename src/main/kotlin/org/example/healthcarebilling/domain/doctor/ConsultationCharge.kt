package org.example.healthcarebilling.domain.doctor

import java.math.BigDecimal

data class ConsultationCharge(
    val specialty: String,
    val minYearsExperience: Int,
    val maxYearsExperience: Int?,
    val amount: BigDecimal
) {
    fun isApplicable(specialty: String, yearsOfExperience: Int): Boolean {
        val specialtyMatches = this.specialty.equals(specialty, ignoreCase = true)
        val experienceInRange = if (maxYearsExperience == null) {
            yearsOfExperience >= minYearsExperience
        } else {
            yearsOfExperience in minYearsExperience..maxYearsExperience
        }
        return specialtyMatches && experienceInRange
    }
}

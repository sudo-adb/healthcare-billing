package org.example.healthcarebilling.data.doctor

import jakarta.persistence.*
import org.example.healthcarebilling.domain.doctor.ConsultationCharge
import java.math.BigDecimal

@Entity
@Table(name = "consultation_charge")
data class ConsultationChargeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val specialty: String,

    @Column(nullable = false)
    val minYearsExperience: Int,

    @Column(nullable = true)
    val maxYearsExperience: Int?,

    @Column(nullable = false, precision = 10, scale = 2)
    val amount: BigDecimal
) {
    fun toDomain(): ConsultationCharge {
        return ConsultationCharge(
            specialty = specialty,
            minYearsExperience = minYearsExperience,
            maxYearsExperience = maxYearsExperience,
            amount = amount
        )
    }

    companion object {
        fun fromDomain(consultationCharge: ConsultationCharge): ConsultationChargeEntity {
            return ConsultationChargeEntity(
                specialty = consultationCharge.specialty,
                minYearsExperience = consultationCharge.minYearsExperience,
                maxYearsExperience = consultationCharge.maxYearsExperience,
                amount = consultationCharge.amount
            )
        }
    }
}


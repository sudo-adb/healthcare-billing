package org.life.healthcarebilling.domain.billing

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*

data class Bill private constructor(
    val id: UUID,
    val patientId: UUID,
    val doctorId: UUID,
    val consultationCharge: BigDecimal,
    val discountPercentage: Int,
    val taxPercentage: Int,
    val billingDate: LocalDateTime,
    val copayPercentage: Int,
) {
    constructor(
        patientId: UUID,
        doctorId: UUID,
        consultationCharge: BigDecimal,
        discountPercentage: Int,
        taxPercentage: Int,
        copayPercentage: Int
    ) : this(
        id = UUID.randomUUID(),
        patientId = patientId,
        doctorId = doctorId,
        consultationCharge = consultationCharge,
        discountPercentage = discountPercentage,
        taxPercentage = taxPercentage,
        billingDate = LocalDateTime.now(),
        copayPercentage = copayPercentage
    )

    val discountAmount: BigDecimal = consultationCharge
        .multiply(BigDecimal(discountPercentage))
        .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

    val amountAfterDiscount: BigDecimal = consultationCharge - discountAmount

    val taxAmount: BigDecimal = amountAfterDiscount
        .multiply(BigDecimal(taxPercentage))
        .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

    val finalAmount: BigDecimal = amountAfterDiscount + taxAmount

    val copayAmount: BigDecimal = finalAmount
        .multiply(BigDecimal(copayPercentage))
        .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)
}

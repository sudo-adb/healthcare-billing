package org.example.healthcarebilling.domain.billing

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
    val billingDate: LocalDateTime
) {
    constructor(
        patientId: UUID,
        doctorId: UUID,
        consultationCharge: BigDecimal,
        discountPercentage: Int,
        taxPercentage: Int
    ) : this(
        id = UUID.randomUUID(),
        patientId = patientId,
        doctorId = doctorId,
        consultationCharge = consultationCharge,
        discountPercentage = discountPercentage,
        taxPercentage = taxPercentage,
        billingDate = LocalDateTime.now()
    )

    val discountAmount: BigDecimal = consultationCharge
        .multiply(BigDecimal(discountPercentage))
        .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

    val amountAfterDiscount: BigDecimal = consultationCharge - discountAmount

    val taxAmount: BigDecimal = amountAfterDiscount
        .multiply(BigDecimal(taxPercentage))
        .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

    val finalAmount: BigDecimal = amountAfterDiscount + taxAmount
}

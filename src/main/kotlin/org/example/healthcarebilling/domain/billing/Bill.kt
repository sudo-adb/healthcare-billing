package org.example.healthcarebilling.domain.billing

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Bill private constructor(
    val id: UUID,
    val patientId: UUID,
    val doctorId: UUID,
    val consultationCharge: BigDecimal,
    val billingDate: LocalDateTime
) {
    constructor(
        patientId: UUID,
        doctorId: UUID,
        consultationCharge: BigDecimal
    ) : this(
        id = UUID.randomUUID(),
        patientId = patientId,
        doctorId = doctorId,
        consultationCharge = consultationCharge,
        billingDate = LocalDateTime.now()
    )
}

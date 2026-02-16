package org.life.healthcarebilling.api.billing

import java.util.*

data class GenerateBillRequest(
    val patientId: UUID,
    val doctorId: UUID
)

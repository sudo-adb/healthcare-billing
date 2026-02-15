package org.example.healthcarebilling.domain.patient.insurance

import java.util.*

data class Insurance(
    val id: UUID,
    val binNumber: String,
    val pcnNumber: String,
    val memberId: UUID
) {
    constructor(memberId: UUID, binNumber: String, pcnNumber: String) : this(
        id = UUID.randomUUID(),
        binNumber = binNumber,
        pcnNumber = pcnNumber,
        memberId = memberId
    )
}

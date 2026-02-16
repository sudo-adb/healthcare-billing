package org.life.healthcarebilling.domain.doctor

import java.time.LocalDate
import java.time.Period
import java.util.UUID

data class Doctor(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val npiNumber: String,
    val specialty: String,
    val practiceStartDate: LocalDate
) {
    constructor(
        firstName: String,
        lastName: String,
        npiNumber: String,
        specialty: String,
        practiceStartDate: LocalDate
    ) : this(
        id = UUID.randomUUID(),
        firstName = firstName,
        lastName = lastName,
        npiNumber = npiNumber,
        specialty = specialty,
        practiceStartDate = practiceStartDate
    )

    val yearsOfExperience: Int get() = Period.between(practiceStartDate, LocalDate.now()).years
}

package org.life.healthcarebilling.domain.patient

import org.life.healthcarebilling.domain.patient.insurance.Insurance
import java.time.LocalDate
import java.time.Period
import java.util.UUID

data class Patient(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    var insurance: Insurance? = null,
) {
    constructor(firstName: String, lastName: String, dateOfBirth: LocalDate) : this(
        UUID.randomUUID(),
        firstName,
        lastName,
        dateOfBirth
    )

    val age: Int get() = Period.between(dateOfBirth, LocalDate.now()).years
}

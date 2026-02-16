package org.life.healthcarebilling.domain.patient

import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import java.time.Period
import kotlin.test.Test

class PatientTest {

    @Test
    fun `given a patient should calculate age correctly`() {
        val patient = Patient(
            firstName = "John",
            lastName = "Smith",
            dateOfBirth = LocalDate.of(1990, 6, 15)
        )

        val expectedAge = Period.between(patient.dateOfBirth, LocalDate.now()).years
        assertEquals(expectedAge, patient.age)
    }
}
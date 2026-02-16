package org.life.healthcarebilling.domain.doctor

import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import java.time.Period
import kotlin.test.Test

class DoctorTest {

    @Test
    fun `given a doctor should calculate years of experience correctly`() {
        val doctor = Doctor(
            firstName = "Jane",
            lastName = "Smith",
            npiNumber = "1234567890",
            specialty = "Cardiology",
            practiceStartDate = LocalDate.of(2015, 6, 15)
        )

        val expectedYears = Period.between(doctor.practiceStartDate, LocalDate.now()).years
        assertEquals(expectedYears, doctor.yearsOfExperience)
    }
}

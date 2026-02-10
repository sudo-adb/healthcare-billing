package org.example.healthcarebilling.domain.doctor

import org.example.healthcarebilling.data.doctor.InMemoryDoctorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.Test

class CreateDoctorUseCaseTest {

    val doctorRepository = InMemoryDoctorRepository()
    val createDoctorUseCase = CreateDoctorUseCase(doctorRepository)

    val testDoctorFirstName = "Jane"
    val testDoctorLastName = "Smith"
    val testDoctorNpiNumber = "1234567890"
    val testDoctorSpecialty = "Cardiology"
    val testDoctorPracticeStartDate = "2015-06-15"

    @Test
    fun `should create doctor with required information`() {
        val doctor = createDoctorUseCase(
            testDoctorFirstName,
            testDoctorLastName,
            testDoctorNpiNumber,
            testDoctorSpecialty,
            testDoctorPracticeStartDate
        )

        assertNotNull(doctor.id)
        assertEquals(testDoctorFirstName, doctor.firstName)
        assertEquals(testDoctorLastName, doctor.lastName)
        assertEquals(testDoctorNpiNumber, doctor.npiNumber)
        assertEquals(testDoctorSpecialty, doctor.specialty)
        assertEquals(testDoctorPracticeStartDate, doctor.practiceStartDate.toString())
    }
}

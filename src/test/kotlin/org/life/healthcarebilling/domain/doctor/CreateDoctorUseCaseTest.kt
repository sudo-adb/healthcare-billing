package org.life.healthcarebilling.domain.doctor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class CreateDoctorUseCaseTest {

    @Autowired
    private lateinit var createDoctorUseCase: CreateDoctorUseCase

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

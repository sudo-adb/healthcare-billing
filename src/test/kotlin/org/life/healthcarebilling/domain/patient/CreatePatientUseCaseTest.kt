package org.life.healthcarebilling.domain.patient

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class CreatePatientUseCaseTest {

    @Autowired
    private lateinit var createPatientUseCase: CreatePatientUseCase

    val testPatientFirstName = "John"
    val testPatientLastName = "Smith"
    val testPatientDob = "2000-01-01"

    @Test
    fun `should create patient with required information`() {
        val patient = createPatientUseCase(testPatientFirstName, testPatientLastName, testPatientDob)

        assertNotNull(patient.id)
        assertEquals(testPatientFirstName, patient.firstName)
        assertEquals(testPatientLastName, patient.lastName)
        assertEquals(testPatientDob, patient.dateOfBirth.toString())
    }
}
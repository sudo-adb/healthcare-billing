package org.example.healthcarebilling.domain.patient

import org.example.healthcarebilling.data.patient.InMemoryPatientRepository
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CreatePatientUseCaseTest {

    val patientRepository = InMemoryPatientRepository()
    val createPatientUseCase = CreatePatientUseCase(patientRepository)

    val testPatientFirstName = "John"
    val testPatientLastName = "Smith"
    val testPatientDob = "2000-01-01"

    @Test
    fun `should create patient with required information`() {
        val patient = createPatientUseCase(testPatientFirstName, testPatientLastName, testPatientDob)
        assertEquals(testPatientFirstName, patient.firstName)
        assertEquals(testPatientLastName, patient.lastName)
        assertEquals(testPatientDob, patient.dateOfBirth)
    }
}
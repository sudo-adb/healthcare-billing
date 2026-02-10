package org.example.healthcarebilling.domain.insurance

import org.example.healthcarebilling.data.patient.InMemoryPatientRepository
import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.insurance.AddPatientInsuranceUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertNotNull
import java.time.LocalDate
import kotlin.test.Test


class AddPatientInsuranceUseCaseTest {


    val patientRepository = InMemoryPatientRepository()
    val addPatientInsuranceUseCase = AddPatientInsuranceUseCase(patientRepository)

    @Test
    fun `should add insurance to patient`() {
        val patient = patientRepository.save(
            Patient(
                firstName = "John",
                lastName = "Smith",
                dateOfBirth = LocalDate.of(1990, 6, 15)
            )
        )


        val binNumber = "1234567890"
        val pcnNumber = "1234567890"

        addPatientInsuranceUseCase(patient.id, binNumber, pcnNumber)

        assertNotNull(patient.insurance)
        assertNotNull(patient.insurance?.id)
        assertEquals(patient.insurance?.binNumber, binNumber)
        assertEquals(patient.insurance?.pcnNumber, pcnNumber)

    }
}
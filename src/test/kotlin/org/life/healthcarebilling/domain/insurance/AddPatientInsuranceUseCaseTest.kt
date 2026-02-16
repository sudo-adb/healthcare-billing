package org.life.healthcarebilling.domain.insurance

import org.life.healthcarebilling.domain.patient.Patient
import org.life.healthcarebilling.domain.patient.PatientRepository
import org.life.healthcarebilling.domain.patient.insurance.AddPatientInsuranceUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.test.Test

@SpringBootTest
@Transactional
class AddPatientInsuranceUseCaseTest {

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @Autowired
    private lateinit var addPatientInsuranceUseCase: AddPatientInsuranceUseCase

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

        val updatedPatient = patientRepository.findById(patient.id)

        assertNotNull(updatedPatient)
        assertNotNull(updatedPatient.insurance)
        assertNotNull(updatedPatient.insurance?.id)
        assertEquals(binNumber, updatedPatient.insurance?.binNumber)
        assertEquals(pcnNumber, updatedPatient.insurance?.pcnNumber)

    }
}
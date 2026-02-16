package org.life.healthcarebilling.domain.patient

import org.life.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.test.Test

@SpringBootTest
@Transactional
class GetPatientUseCaseTest {

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @Autowired
    private lateinit var getPatientUseCase: GetPatientUseCase

    @Test
    fun `should return patient when found by firstName lastName and dateOfBirth`() {
        val firstName = "John"
        val lastName = "Doe"
        val dateOfBirth = "1990-06-15"

        patientRepository.save(patient1)

        val foundPatient = getPatientUseCase(firstName, lastName, LocalDate.parse(dateOfBirth))

        assertNotNull(foundPatient)
        assertEquals(firstName, foundPatient?.firstName)
        assertEquals(lastName, foundPatient?.lastName)
        assertEquals(dateOfBirth, foundPatient?.dateOfBirth.toString())
    }

    @Test
    fun `should return null when patient not found`() {
        val firstName = "Jane"
        val lastName = "Smith"
        val dateOfBirth = LocalDate.of(1985, 3, 20)

        val foundPatient = getPatientUseCase(firstName, lastName, dateOfBirth)

        assertNull(foundPatient)
    }

    @Test
    fun `should return null when firstName matches but lastName does not match`() {

        patientRepository.save(patient1)

        val foundPatient = getPatientUseCase(patient1.firstName, "Smith", patient1.dateOfBirth)

        assertNull(foundPatient)
    }

    @Test
    fun `should return null when firstName and lastName match but dateOfBirth does not match`() {
        patientRepository.save(patient1)

        val foundPatient = getPatientUseCase(patient1.firstName, patient1.lastName, LocalDate.now())

        assertNull(foundPatient)
    }

}

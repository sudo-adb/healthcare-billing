package org.example.healthcarebilling.domain.doctor


import org.example.healthcarebilling.doctor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GetDoctorByNpiNumberUseCaseTest {

    @Autowired
    private lateinit var doctorRepository: DoctorRepository

    @Autowired
    private lateinit var getDoctorByNpiNumberUseCase: GetDoctorByNpiNumberUseCase

    @Test
    fun `should find doctor details by NPI number`() {

        val npiNumber = doctor.npiNumber
        val savedDoctor = doctorRepository.save(doctor)
        val foundDoctor = getDoctorByNpiNumberUseCase(npiNumber)

        assertNotNull(doctor)
        assertEquals(savedDoctor.id, foundDoctor?.id)
        assertEquals(savedDoctor.firstName, foundDoctor?.firstName)
        assertEquals(savedDoctor.lastName, foundDoctor?.lastName)
        assertEquals(savedDoctor.npiNumber, foundDoctor?.npiNumber)
        assertEquals(savedDoctor.specialty, foundDoctor?.specialty)
        assertEquals(savedDoctor.practiceStartDate, foundDoctor?.practiceStartDate)
    }

    @Test
    fun `should return null when doctor with given NPI number does not exist`() {
        val nonExistentNpiNumber = "0000000000"
        val foundDoctor = getDoctorByNpiNumberUseCase(nonExistentNpiNumber)

        assertEquals(null, foundDoctor)
    }
}
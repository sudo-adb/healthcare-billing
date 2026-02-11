package org.example.healthcarebilling.domain.doctor

import org.example.healthcarebilling.data.doctor.InMemoryConsultationChargeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import kotlin.test.Test

class GetConsultationChargeUseCaseTest {

    private val consultationChargeRepository = InMemoryConsultationChargeRepository()
    private val getConsultationChargeUseCase = GetConsultationChargeUseCase(consultationChargeRepository)

    @Test
    fun `should calculate Ortho charge for less than 20 years experience`() {
        val charge = getConsultationChargeUseCase("Ortho", 10)


        assertEquals(BigDecimal("800.00"), charge)
    }

    @Test
    fun `should calculate Ortho charge for 20 to 30 years experience`() {
        val charge = getConsultationChargeUseCase("Ortho", 25)

        assertEquals(BigDecimal("1000.00"), charge)
    }

    @Test
    fun `should calculate Ortho charge for more than 30 years experience`() {
        val charge = getConsultationChargeUseCase("Ortho", 35)
        assertEquals(BigDecimal("1200.00"), charge)
    }

    @Test
    fun `should calculate Cardio charge for less than 20 years experience`() {
        val charge = getConsultationChargeUseCase("Cardio", 15)
        assertEquals(BigDecimal("1000.00"), charge)
    }

    @Test
    fun `should calculate Cardio charge for 20 to 30 years experience`() {
        val charge = getConsultationChargeUseCase("Cardio", 25)

        assertEquals(BigDecimal("1500.00"), charge)
    }

    @Test
    fun `should calculate Cardio charge for more than 30 years experience`() {
        val charge = getConsultationChargeUseCase("Cardio", 35)

        assertEquals(BigDecimal("2000.00"), charge)
    }

    @Test
    fun `should calculate General charge for more than 30 years experience`() {
        val charge = getConsultationChargeUseCase("GENERAL", 35)

        assertEquals(BigDecimal("700.00"), charge)
    }

    @Test
    fun `should handle case insensitive specialty matching`() {
        val expected = BigDecimal("800.00")

        assertEquals(expected, getConsultationChargeUseCase("ORTHO", 15))
        assertEquals(expected, getConsultationChargeUseCase("ortho", 15))
        assertEquals(expected, getConsultationChargeUseCase("OrThO", 15))
    }
}

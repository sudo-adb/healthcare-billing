package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.data.doctor.InMemoryConsultationChargeRepository
import org.example.healthcarebilling.data.doctor.InMemoryDoctorRepository
import org.example.healthcarebilling.data.patient.InMemoryPatientRepository
import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.domain.doctor.GetConsultationChargeUseCase
import org.example.healthcarebilling.highExpOrthoDoctor
import org.example.healthcarebilling.mediumExpCardioDoctor
import org.example.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class CalculateBillUseCaseTest {

    private val patientRepository = InMemoryPatientRepository()
    private val doctorRepository = InMemoryDoctorRepository()
    private val consultationChargeRepository = InMemoryConsultationChargeRepository()

    private val getConsultationChargeUseCase = GetConsultationChargeUseCase(consultationChargeRepository)
    private val calculateBillUseCase = CalculateBillUseCase(patientRepository, doctorRepository, getConsultationChargeUseCase)

    @Test
    fun `should calculate bill for patient consultation with doctor`() {

        val patient = patientRepository.save(patient1)
        val doctor = doctorRepository.save(doctor)

        val bill = calculateBillUseCase(patient.id, doctor.id)

        assertNotNull(bill.id)
        assertEquals(patient.id, bill.patientId)
        assertEquals(doctor.id, bill.doctorId)
        assertEquals(BigDecimal("1000.00"), bill.consultationCharge) // Cardio < 20 years = 1000
        assertNotNull(bill.billingDate)
    }

    @Test
    fun `should calculate bill with correct charge for Cardio specialist with 25 years experience`() {
        val patient = patientRepository.save(patient1)
        val doctor = doctorRepository.save(mediumExpCardioDoctor)

        val bill = calculateBillUseCase(patient.id, doctor.id)

        assertNotNull(bill.id)
        assertEquals(patient.id, bill.patientId)
        assertEquals(doctor.id, bill.doctorId)
        assertEquals(BigDecimal("1500.00"), bill.consultationCharge) // Cardio 20-30 years = 1500
    }

    @Test
    fun `should calculate bill with correct charge for Ortho specialist with 35 years experience`() {
        val patient = patientRepository.save(patient1)
        val doctor = doctorRepository.save(highExpOrthoDoctor)

        val bill = calculateBillUseCase(patient.id, doctor.id)

        assertNotNull(bill.id)
        assertEquals(patient.id, bill.patientId)
        assertEquals(doctor.id, bill.doctorId)
        assertEquals(BigDecimal("1200.00"), bill.consultationCharge) // Ortho > 30 years = 1200
    }

    @Test
    fun `should throw exception when patient not found`() {
        val nonExistentPatientId = UUID.randomUUID()

        val exception = assertThrows<IllegalArgumentException> {
            calculateBillUseCase(nonExistentPatientId, doctor.id)
        }

        assertEquals("Patient with id $nonExistentPatientId not found", exception.message)
    }

    @Test
    fun `should throw exception when doctor not found`() {
        patientRepository.save(patient1)
        val nonExistentDoctorId = UUID.randomUUID()

        val exception = assertThrows<IllegalArgumentException> {
            calculateBillUseCase(patient1.id, nonExistentDoctorId)
        }

        assertEquals("Doctor with id $nonExistentDoctorId not found", exception.message)
    }
}

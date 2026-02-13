package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.data.appointment.InMemoryAppointmentRepository
import org.example.healthcarebilling.data.doctor.InMemoryConsultationChargeRepository
import org.example.healthcarebilling.data.doctor.InMemoryDoctorRepository
import org.example.healthcarebilling.data.patient.InMemoryPatientRepository
import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentStatus
import org.example.healthcarebilling.domain.appointment.GetCompletedAppointmentCountUseCase
import org.example.healthcarebilling.domain.doctor.GetConsultationChargeUseCase
import org.example.healthcarebilling.highExpOrthoDoctor
import org.example.healthcarebilling.mediumExpCardioDoctor
import org.example.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class CalculateBillUseCaseTest {

    private val patientRepository = InMemoryPatientRepository()
    private val doctorRepository = InMemoryDoctorRepository()
    private val consultationChargeRepository = InMemoryConsultationChargeRepository()
    private val appointmentRepository = InMemoryAppointmentRepository()

    private val getConsultationChargeUseCase = GetConsultationChargeUseCase(consultationChargeRepository)
    val getCompletedAppointmentCountUseCase = GetCompletedAppointmentCountUseCase(appointmentRepository)

    val maxDiscount = 10
    private val getDiscountUseCase = GetDiscountUseCase(getCompletedAppointmentCountUseCase, maxDiscount = maxDiscount)

    val taxPercentage = 12
    private val getTaxUseCase = GetTaxUseCase(taxPercentage)

    val copayPercentage = 10
    private val getCopayUseCase = GetCopayUseCase(copayPercentage)

    private val calculateBillUseCase = CalculateBillUseCase(
        patientRepository,
        doctorRepository,
        getConsultationChargeUseCase,
        getDiscountUseCase,
        getTaxUseCase,
        getCopayUseCase,
    )

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

    @Test
    fun `should calculate bill with 5 percent discount for patient with 5 completed appointments,applicable tax and copayAmount`() {
        val patient = patientRepository.save(patient1)
        val doctor = doctorRepository.save(doctor)

        // Create 5 completed appointments for the patient
        repeat(5) {
            val appointment = Appointment(
                patientId = patient.id,
                doctorId = doctor.id,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val bill = calculateBillUseCase(patient.id, doctor.id)

        assertEquals(BigDecimal("1000.00"), bill.consultationCharge)
        assertEquals(5, bill.discountPercentage)
        assertEquals(BigDecimal("50.00"), bill.discountAmount)
        assertEquals(BigDecimal("950.00"), bill.amountAfterDiscount)
        assertEquals(taxPercentage, bill.taxPercentage)
        assertEquals(BigDecimal("114.00"), bill.taxAmount)
        assertEquals(BigDecimal("1064.00"), bill.finalAmount)
        assertEquals(BigDecimal("106.40"), bill.copayAmount)
    }


    @Test
    fun `should calculate bill with cap discount at max percent even if patient has more completed appointments,applicable tax and copayAmount`() {
        val patient = patientRepository.save(patient1)
        val doctor = doctorRepository.save(highExpOrthoDoctor)

        // Create 15 completed appointments for the patient
        repeat(15) {
            val appointment = Appointment(
                patientId = patient.id,
                doctorId = doctor.id,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val bill = calculateBillUseCase(patient.id, doctor.id)

        assertEquals(BigDecimal("1200.00"), bill.consultationCharge)
        assertEquals(maxDiscount, bill.discountPercentage)
        assertEquals(BigDecimal("120.00"), bill.discountAmount)
        assertEquals(BigDecimal("1080.00"), bill.amountAfterDiscount)
        assertEquals(taxPercentage, bill.taxPercentage)
        assertEquals(BigDecimal("129.60"), bill.taxAmount)
        assertEquals(BigDecimal("1209.60"), bill.finalAmount)
        assertEquals(BigDecimal("120.96"), bill.copayAmount)

    }

}

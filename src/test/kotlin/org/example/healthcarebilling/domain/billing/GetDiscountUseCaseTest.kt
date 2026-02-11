package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.data.appointment.InMemoryAppointmentRepository
import org.example.healthcarebilling.data.doctor.InMemoryDoctorRepository
import org.example.healthcarebilling.data.patient.InMemoryPatientRepository
import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentStatus
import org.example.healthcarebilling.domain.appointment.GetCompletedAppointmentCountUseCase
import org.example.healthcarebilling.mediumExpCardioDoctor
import org.example.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class GetDiscountUseCaseTest {

    private val appointmentRepository = InMemoryAppointmentRepository()
    private val patientRepository = InMemoryPatientRepository()
    private val doctorRepository = InMemoryDoctorRepository()
    private val getCompletedAppointmentCountUseCase = GetCompletedAppointmentCountUseCase(appointmentRepository)
    private val maxDiscount = 10
    private val getDiscountUseCase = GetDiscountUseCase(getCompletedAppointmentCountUseCase, maxDiscount)

    @Test
    fun `should return 0 discount when patient has no completed appointments`() {
        val patient = patientRepository.save(patient1)

        val discount = getDiscountUseCase(patient.id)

        assertEquals(0, discount)
    }

    @Test
    fun `should return discount equal to completed appointments when less than 10`() {
        val patientId = patientRepository.save(patient1).id
        val doctorId = doctorRepository.save(doctor).id

        // Create 5 completed appointments
        repeat(5) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val discount = getDiscountUseCase(patientId)

        assertEquals(5, discount)
    }

    @Test
    fun `should return maximum 10 discount when patient has more than 10 completed appointments`() {
        val patientId = patientRepository.save(patient1).id
        val doctorId = doctorRepository.save(doctor).id

        // Create 15 completed appointments
        repeat(15) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val discount = getDiscountUseCase(patientId)

        assertEquals(10, discount)
    }

    @Test
    fun `should count only completed appointments not scheduled or cancelled`() {
        val patientId = UUID.randomUUID()
        val doctorId = UUID.randomUUID()

        // Create 3 completed appointments
        repeat(3) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        // Create 1 scheduled appointments
        repeat(1) {
            appointmentRepository.save(
                Appointment(
                    patientId = patientId,
                    doctorId = doctorId,
                    appointmentDateTime = LocalDateTime.of(2026, 2, it + 1, 10, 0)
                )
            )
        }

        // Create 2 cancelled appointments
        repeat(2) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 3, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.CANCELLED
            appointmentRepository.save(appointment)
        }

        val discount = getDiscountUseCase(patientId)

        assertEquals(3, discount)
    }

    @Test
    fun `should return discount equal to completed appointments when less than 10 irrespective of doctor`() {
        val patientId = patientRepository.save(patient1).id
        val doctor1Id = doctorRepository.save(doctor).id
        val doctor2Id = doctorRepository.save(mediumExpCardioDoctor).id

        // Create 5 completed appointments with doctor 1
        repeat(5) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctor1Id,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        // Create 2 completed appointments with doctor 2
        repeat(2) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctor2Id,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val discount = getDiscountUseCase(patientId)

        assertEquals(7, discount)
    }

    @Test
    fun `should cap discount at custom max discount`() {
        val customMaxDiscount = 5
        val customGetDiscountUseCase = GetDiscountUseCase(getCompletedAppointmentCountUseCase, customMaxDiscount)
        val patientId = patientRepository.save(patient1).id
        val doctorId = doctorRepository.save(doctor).id

        // Create 8 completed appointments
        repeat(8) {
            val appointment = Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }

        val discount = customGetDiscountUseCase(patientId)

        assertEquals(5, discount)
    }
}

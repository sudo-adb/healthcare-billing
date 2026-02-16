package org.life.healthcarebilling.domain.billing

import org.life.healthcarebilling.doctor
import org.life.healthcarebilling.domain.appointment.Appointment
import org.life.healthcarebilling.domain.appointment.AppointmentRepository
import org.life.healthcarebilling.domain.appointment.AppointmentStatus
import org.life.healthcarebilling.domain.appointment.GetCompletedAppointmentCountUseCase
import org.life.healthcarebilling.domain.doctor.DoctorRepository
import org.life.healthcarebilling.domain.patient.PatientRepository
import org.life.healthcarebilling.mediumExpCardioDoctor
import org.life.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GetDiscountUseCaseTest {

    @Autowired
    private lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @Autowired
    private lateinit var doctorRepository: DoctorRepository

    @Autowired
    private lateinit var getCompletedAppointmentCountUseCase: GetCompletedAppointmentCountUseCase

    @Autowired
    private lateinit var getDiscountUseCase: GetDiscountUseCase

    private val maxDiscount = 10

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

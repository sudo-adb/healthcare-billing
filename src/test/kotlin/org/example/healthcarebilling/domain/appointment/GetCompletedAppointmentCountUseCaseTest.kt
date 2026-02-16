package org.example.healthcarebilling.domain.appointment

import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.patient1
import org.example.healthcarebilling.patient2
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
class GetCompletedAppointmentCountUseCaseTest {

    @Autowired
    private lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    private lateinit var getCompletedAppointmentCountUseCase: GetCompletedAppointmentCountUseCase

    @Test
    fun `should return 0 when patient has no appointments`() {
        val patientId = patient1.id
        val count = getCompletedAppointmentCountUseCase(patientId)
        assertEquals(0, count)
    }

    @Test
    fun `should return 0 when patient has only scheduled appointments`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        appointmentRepository.save(
            Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, 15, 10, 0)
            )
        )
        val count = getCompletedAppointmentCountUseCase(patientId)
        assertEquals(0, count)
    }

    @Test
    fun `should return count of completed appointments for patient`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        val appointment1 = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = LocalDateTime.of(2026, 1, 10, 10, 0)
        )
        appointment1.status = AppointmentStatus.COMPLETED
        appointmentRepository.save(appointment1)
        val appointment2 = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = LocalDateTime.of(2026, 1, 15, 14, 0)
        )
        appointment2.status = AppointmentStatus.COMPLETED
        appointmentRepository.save(appointment2)
        val count = getCompletedAppointmentCountUseCase(patientId)
        assertEquals(2, count)
    }

    @Test
    fun `should count only completed appointments not scheduled or cancelled`() {
        val patientId = UUID.randomUUID()
        val doctorId = UUID.randomUUID()
        val completedAppointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = LocalDateTime.of(2026, 1, 5, 10, 0)
        )
        completedAppointment.status = AppointmentStatus.COMPLETED
        appointmentRepository.save(completedAppointment)
        appointmentRepository.save(
            Appointment(
                patientId = patientId,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 2, 15, 10, 0)
            )
        )
        val cancelledAppointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = LocalDateTime.of(2026, 1, 20, 10, 0)
        )
        cancelledAppointment.status = AppointmentStatus.CANCELLED
        appointmentRepository.save(cancelledAppointment)
        val count = getCompletedAppointmentCountUseCase(patientId)
        assertEquals(1, count)
    }

    @Test
    fun `should count only appointments for specific patient`() {
        val patient1Id = patient1.id
        val patient2Id = patient2.id
        val doctorId = doctor.id
        repeat(3) {
            val appointment = Appointment(
                patientId = patient1Id,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 1, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }
        repeat(2) {
            val appointment = Appointment(
                patientId = patient2Id,
                doctorId = doctorId,
                appointmentDateTime = LocalDateTime.of(2026, 1, it + 10, 10, 0)
            )
            appointment.status = AppointmentStatus.COMPLETED
            appointmentRepository.save(appointment)
        }
        val count1 = getCompletedAppointmentCountUseCase(patient1Id)
        val count2 = getCompletedAppointmentCountUseCase(patient2Id)
        assertEquals(3, count1)
        assertEquals(2, count2)
    }
}

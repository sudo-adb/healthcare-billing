package org.life.healthcarebilling.domain.appointment

import org.life.healthcarebilling.doctor
import org.life.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

@SpringBootTest
@Transactional
class UpdateAppointmentStatusUseCaseTest {

    @Autowired
    private lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    private lateinit var updateAppointmentStatusUseCase: UpdateAppointmentStatusUseCase

    @Test
    fun `should update appointment status to COMPLETED`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        val appointmentDateTime = LocalDateTime.now().plusDays(1)

        val appointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = appointmentDateTime
        )
        val savedAppointment = appointmentRepository.save(appointment)

        assertEquals(AppointmentStatus.SCHEDULED, savedAppointment.status)

        val updatedAppointment = updateAppointmentStatusUseCase(savedAppointment.id, AppointmentStatus.COMPLETED)

        assertNotNull(updatedAppointment)
        assertEquals(savedAppointment.id, updatedAppointment.id)
        assertEquals(AppointmentStatus.COMPLETED, updatedAppointment.status)
    }

    @Test
    fun `should update appointment status to CANCELLED`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        val appointmentDateTime = LocalDateTime.now().plusDays(1)

        val appointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = appointmentDateTime
        )
        val savedAppointment = appointmentRepository.save(appointment)

        val updatedAppointment = updateAppointmentStatusUseCase(savedAppointment.id, AppointmentStatus.CANCELLED)

        assertNotNull(updatedAppointment)
        assertEquals(savedAppointment.id, updatedAppointment.id)
        assertEquals(AppointmentStatus.CANCELLED, updatedAppointment.status)
    }

    @Test
    fun `should throw exception when appointment not found`() {
        val nonExistentId = UUID.randomUUID()

        val exception = assertThrows<IllegalArgumentException> {
            updateAppointmentStatusUseCase(nonExistentId, AppointmentStatus.COMPLETED)
        }

        assertEquals("Appointment with id $nonExistentId not found", exception.message)
    }
}

package org.life.healthcarebilling.domain.appointment

import org.life.healthcarebilling.doctor
import org.life.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import java.time.LocalDateTime
import kotlin.test.Test

class AppointmentTest {

    @Test
    fun `should create appointment with required fields and SCHEDULED status`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        val appointmentDateTime = LocalDateTime.of(2026, 2, 15, 10, 30)

        val appointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = appointmentDateTime
        )

        assertNotNull(appointment.id)
        assertEquals(patientId, appointment.patientId)
        assertEquals(doctorId, appointment.doctorId)
        assertEquals(appointmentDateTime, appointment.appointmentTime)
        assertEquals(AppointmentStatus.SCHEDULED, appointment.status)
    }
}

package org.example.healthcarebilling.domain.appointment

import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.patient1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.test.Test

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateAppointmentUseCaseTest {

    @Autowired
    private lateinit var createAppointmentUseCase: CreateAppointmentUseCase

    @Test
    fun `should create appointment with patient and doctor with SCHEDULED status`() {
        val patientId = patient1.id
        val doctorId = doctor.id
        val appointmentDateTime = LocalDateTime.now().plusDays(1)

        val appointment = createAppointmentUseCase(
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

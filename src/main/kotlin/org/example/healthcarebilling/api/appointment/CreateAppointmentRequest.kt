package org.example.healthcarebilling.api.appointment

import jakarta.validation.constraints.Future
import java.time.LocalDateTime
import java.util.*

data class CreateAppointmentRequest(
    val patientId: UUID,
    val doctorId: UUID,
    @Future(message = "Appointment date and time must be in the future")
    val appointmentDateTime: LocalDateTime
)

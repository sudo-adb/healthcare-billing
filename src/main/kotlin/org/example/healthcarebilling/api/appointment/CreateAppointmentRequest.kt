package org.example.healthcarebilling.api.appointment

import java.time.LocalDateTime
import java.util.UUID

data class CreateAppointmentRequest(
    val patientId: UUID,
    val doctorId: UUID,
    val appointmentDateTime: LocalDateTime
)

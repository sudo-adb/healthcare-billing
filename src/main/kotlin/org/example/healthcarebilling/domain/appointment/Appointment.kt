package org.example.healthcarebilling.domain.appointment

import java.time.LocalDateTime
import java.util.UUID

data class Appointment private constructor(
    val id: UUID,
    val patientId: UUID,
    val doctorId: UUID,
    val appointmentTime: LocalDateTime,
    var status: AppointmentStatus
) {
    constructor(
        patientId: UUID,
        doctorId: UUID,
        appointmentDateTime: LocalDateTime
    ) : this(
        id = UUID.randomUUID(),
        patientId = patientId,
        doctorId = doctorId,
        appointmentTime = appointmentDateTime,
        status = AppointmentStatus.SCHEDULED
    )
}

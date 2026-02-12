package org.example.healthcarebilling.api.appointment

import org.example.healthcarebilling.domain.appointment.AppointmentStatus

data class UpdateAppointmentStatusRequest(
    val status: AppointmentStatus
)

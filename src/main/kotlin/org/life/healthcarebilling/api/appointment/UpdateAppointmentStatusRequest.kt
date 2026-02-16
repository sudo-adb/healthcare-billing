package org.life.healthcarebilling.api.appointment

import org.life.healthcarebilling.domain.appointment.AppointmentStatus

data class UpdateAppointmentStatusRequest(
    val status: AppointmentStatus
)

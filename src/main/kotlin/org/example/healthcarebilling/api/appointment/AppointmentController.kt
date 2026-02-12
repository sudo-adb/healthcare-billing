package org.example.healthcarebilling.api.appointment

import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.CreateAppointmentUseCase
import org.example.healthcarebilling.domain.appointment.UpdateAppointmentStatusUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
class AppointmentController @Autowired constructor(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val updateAppointmentStatusUseCase: UpdateAppointmentStatusUseCase
) {

    @PostMapping("/appointments")
    fun createAppointment(@RequestBody request: CreateAppointmentRequest): Appointment {
        return createAppointmentUseCase(
            patientId = request.patientId,
            doctorId = request.doctorId,
            appointmentDateTime = request.appointmentDateTime
        )
    }

    @PatchMapping("/appointments/{id}/status")
    fun updateAppointmentStatus(
        @PathVariable id: UUID,
        @RequestBody request: UpdateAppointmentStatusRequest
    ): Appointment {
        return updateAppointmentStatusUseCase(id, request.status)
    }
}

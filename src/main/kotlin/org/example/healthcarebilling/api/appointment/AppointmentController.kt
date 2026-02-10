package org.example.healthcarebilling.api.appointment

import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.CreateAppointmentUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AppointmentController @Autowired constructor(
    private val createAppointmentUseCase: CreateAppointmentUseCase
) {

    @PostMapping("/appointments")
    fun createAppointment(@RequestBody request: CreateAppointmentRequest): Appointment {
        return createAppointmentUseCase(
            patientId = request.patientId,
            doctorId = request.doctorId,
            appointmentDateTime = request.appointmentDateTime
        )
    }
}

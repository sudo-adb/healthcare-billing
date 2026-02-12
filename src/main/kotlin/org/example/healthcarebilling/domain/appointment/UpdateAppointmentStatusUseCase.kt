package org.example.healthcarebilling.domain.appointment

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UpdateAppointmentStatusUseCase(
    private val appointmentRepository: AppointmentRepository
) {

    operator fun invoke(appointmentId: UUID, status: AppointmentStatus): Appointment {
        val appointment = appointmentRepository.findById(appointmentId)
            ?: throw IllegalArgumentException("Appointment with id $appointmentId not found")

        appointment.status = status
        return appointmentRepository.save(appointment)
    }
}

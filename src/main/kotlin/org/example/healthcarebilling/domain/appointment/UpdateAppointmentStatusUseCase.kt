package org.example.healthcarebilling.domain.appointment

import org.springframework.stereotype.Component
import java.util.*

@Component
class UpdateAppointmentStatusUseCase(
    private val appointmentRepository: AppointmentRepository
) {

    operator fun invoke(appointmentId: UUID, status: AppointmentStatus): Appointment {
        val appointment = appointmentRepository.findById(appointmentId)
            ?: throw IllegalArgumentException("Appointment with id $appointmentId not found")

        return appointmentRepository.update(appointment, status)
    }
}

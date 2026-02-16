package org.life.healthcarebilling.domain.appointment

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
class CreateAppointmentUseCase(val appointmentRepository: AppointmentRepository) {

    operator fun invoke(
        patientId: UUID,
        doctorId: UUID,
        appointmentDateTime: LocalDateTime
    ): Appointment {
        val appointment = Appointment(
            patientId = patientId,
            doctorId = doctorId,
            appointmentDateTime = appointmentDateTime
        )
        return appointmentRepository.save(appointment)
    }
}

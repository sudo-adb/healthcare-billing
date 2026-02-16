package org.life.healthcarebilling.domain.appointment

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GetCompletedAppointmentCountUseCase(
    private val appointmentRepository: AppointmentRepository
) {

    operator fun invoke(patientId: UUID): Int {
        return appointmentRepository.countCompletedByPatientId(patientId)
    }
}

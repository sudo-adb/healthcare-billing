package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.domain.appointment.GetCompletedAppointmentCountUseCase
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.math.min

@Component
class GetDiscountUseCase(
    private val getCompletedAppointmentCountUseCase: GetCompletedAppointmentCountUseCase
) {

    operator fun invoke(patientId: UUID): Int {
        val completedAppointmentsCount = getCompletedAppointmentCountUseCase(patientId)
        return min(completedAppointmentsCount, 10)
    }
}

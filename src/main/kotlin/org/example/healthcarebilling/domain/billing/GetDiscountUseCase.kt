package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.domain.appointment.GetCompletedAppointmentCountUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.math.min

@Component
class GetDiscountUseCase(
    private val getCompletedAppointmentCountUseCase: GetCompletedAppointmentCountUseCase,
    @Value($$"${billing.discount.max}") private val maxDiscount: Int
) {
    init {
        println("Max Discount = $maxDiscount")
    }
    operator fun invoke(patientId: UUID): Int {
        val completedAppointmentsCount = getCompletedAppointmentCountUseCase(patientId)
        return min(completedAppointmentsCount, maxDiscount)
    }
}

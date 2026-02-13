package org.example.healthcarebilling.domain.billing

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GetCopayUseCase(
    @Value("\${billing.copay.percentage}") private val copayPercentage: Int
) {

    operator fun invoke(): Int {
        return copayPercentage
    }
}

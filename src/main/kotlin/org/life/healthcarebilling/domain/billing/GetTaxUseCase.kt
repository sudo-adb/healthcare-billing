package org.life.healthcarebilling.domain.billing

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GetTaxUseCase(
    @Value("\${billing.tax.percentage}") private val taxPercentage: Int
) {

    operator fun invoke(): Int {
        return taxPercentage
    }
}

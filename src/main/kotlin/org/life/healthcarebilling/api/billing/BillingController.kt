package org.life.healthcarebilling.api.billing

import org.life.healthcarebilling.domain.billing.Bill
import org.life.healthcarebilling.domain.billing.CalculateBillUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BillingController @Autowired constructor(
    private val calculateBillUseCase: CalculateBillUseCase
) {

    @PostMapping("/bills")
    fun generateBill(@RequestBody request: GenerateBillRequest): Bill {
        return calculateBillUseCase(request.patientId, request.doctorId)
    }
}

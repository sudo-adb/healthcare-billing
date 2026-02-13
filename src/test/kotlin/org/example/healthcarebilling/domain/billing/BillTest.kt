package org.example.healthcarebilling.domain.billing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class BillTest {

    @Test
    fun `should create bill with required fields and no discount and no tax`() {
        val patientId = UUID.randomUUID()
        val doctorId = UUID.randomUUID()
        val consultationCharge = BigDecimal("800.00")
        val discountPercentage = 0
        val taxPercentage = 0
        val copayPercentage = 0

        val bill = Bill(
            patientId = patientId,
            doctorId = doctorId,
            consultationCharge = consultationCharge,
            discountPercentage = discountPercentage,
            taxPercentage = taxPercentage,
            copayPercentage = copayPercentage
        )

        assertNotNull(bill.id)
        assertEquals(patientId, bill.patientId)
        assertEquals(doctorId, bill.doctorId)
        assertEquals(consultationCharge, bill.consultationCharge)
        assertEquals(0, bill.discountPercentage)
        assertEquals(BigDecimal("0.00"), bill.discountAmount)
        assertEquals(BigDecimal("800.00"), bill.finalAmount)
        assertNotNull(bill.billingDate)
    }

    @Test
    fun `should calculate discount amount and final amount correctly for 10 percent discount and 12 percent tax`() {
        val patientId = UUID.randomUUID()
        val doctorId = UUID.randomUUID()
        val consultationCharge = BigDecimal("1000.00")
        val discountPercentage = 10
        val taxPercentage = 12
        val copayPercentage = 10

        val bill = Bill(
            patientId = patientId,
            doctorId = doctorId,
            consultationCharge = consultationCharge,
            discountPercentage = discountPercentage,
            taxPercentage = taxPercentage,
            copayPercentage = copayPercentage
        )

        assertEquals(10, bill.discountPercentage)
        assertEquals(BigDecimal("100.00"), bill.discountAmount)
        assertEquals(BigDecimal("900.00"), bill.amountAfterDiscount)
        assertEquals(12, bill.taxPercentage)
        assertEquals(BigDecimal("108.00"), bill.taxAmount)
        assertEquals(BigDecimal("1008.00"), bill.finalAmount)
        assertEquals(BigDecimal("100.80"), bill.copayAmount)
    }
}

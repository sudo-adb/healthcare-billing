package org.example.healthcarebilling.api.billing

import org.example.healthcarebilling.api.createDoctorRequest
import org.example.healthcarebilling.api.createPatientRequest
import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentStatus
import org.example.healthcarebilling.domain.billing.Bill
import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.patient.Patient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class BillingControllerTest(@Autowired private val restTestClient: RestTestClient) {

    @LocalServerPort
    private val port = 0

    @Test
    fun `should generate bill for patient consultation with doctor`() {

        val patientResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = patientResponse.responseBody
        assertNotNull(createdPatient)

        val doctorResponse = restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(createDoctorRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Doctor>()
            .returnResult()

        val createdDoctor = doctorResponse.responseBody
        assertNotNull(createdDoctor)

        val generateBillRequest = """
        {
            "patientId": "${createdPatient.id}",
            "doctorId": "${createdDoctor.id}"
        }
        """.trimIndent()

        val billResponse = restTestClient.post()
            .uri("/bills")
            .contentType(APPLICATION_JSON)
            .body(generateBillRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Bill>()
            .returnResult()

        val generatedBill = billResponse.responseBody
        assertNotNull(generatedBill)
        assertNotNull(generatedBill.id)
        assertEquals(createdPatient.id, generatedBill.patientId)
        assertEquals(createdDoctor.id, generatedBill.doctorId)
        assertEquals(BigDecimal("1000.00"), generatedBill.consultationCharge)
        assertEquals(0, generatedBill.discountPercentage)
        assertEquals(BigDecimal("0.00"), generatedBill.discountAmount)
        assertEquals(BigDecimal("1000.00"), generatedBill.amountAfterDiscount)
        assertEquals(12, generatedBill.taxPercentage)
        assertEquals(BigDecimal("120.00"), generatedBill.taxAmount)
        assertEquals(BigDecimal("1120.00"), generatedBill.finalAmount)
        assertNotNull(generatedBill.billingDate)
    }

    @Test
    fun `should return error when patient not found`() {

        val nonExistentPaymentId = UUID.randomUUID().toString()
        val nonExistentDoctorId = UUID.randomUUID().toString()

        val generateBillRequest = """
        {
            "patientId": "$nonExistentPaymentId",
            "doctorId": "$nonExistentDoctorId"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/bills")
            .contentType(APPLICATION_JSON)
            .body(generateBillRequest)
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    fun `should return error when doctor not found`() {

        val nonExistentDoctorId = UUID.randomUUID().toString()

        val patientResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = patientResponse.responseBody
        assertNotNull(createdPatient)


        val generateBillRequest = """
        {
            "patientId": "${createdPatient.id}",
            "doctorId": "$nonExistentDoctorId"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/bills")
            .contentType(APPLICATION_JSON)
            .body(generateBillRequest)
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    fun `should generate bill with 5 percent discount for patient with 5 completed appointments`() {

        val patientResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = patientResponse.responseBody
        assertNotNull(createdPatient)

        val doctorResponse = restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(createDoctorRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Doctor>()
            .returnResult()

        val createdDoctor = doctorResponse.responseBody
        assertNotNull(createdDoctor)

        repeat(5) {
            val createAppointmentRequest = """
        {
            "patientId": "${createdPatient.id}",
            "doctorId": "${createdDoctor.id}",
            "appointmentDateTime": "2026-03-01T11:00:00"
        }
        """.trimIndent()
            val createResponse = restTestClient.post()
                .uri("/appointments")
                .contentType(APPLICATION_JSON)
                .body(createAppointmentRequest)
                .exchange()
                .expectStatus().isOk
                .expectBody<Appointment>()
                .returnResult()

            val createdAppointment = createResponse.responseBody
            assertNotNull(createdAppointment)
            assertEquals(AppointmentStatus.SCHEDULED, createdAppointment.status)

            val updateRequest = """
        {
            "status": "COMPLETED"
        }
        """.trimIndent()

            val updateResponse = restTestClient.patch()
                .uri("/appointments/${createdAppointment.id}/status")
                .contentType(APPLICATION_JSON)
                .body(updateRequest)
                .exchange()
                .expectStatus().isOk
                .expectBody<Appointment>()
                .returnResult()

            val updatedAppointment = updateResponse.responseBody
            assertNotNull(updatedAppointment)
        }

        val generateBillRequest = """
        {
            "patientId": "${createdPatient.id}",
            "doctorId": "${createdDoctor.id}"
        }
        """.trimIndent()

        val billResponse = restTestClient.post()
            .uri("/bills")
            .contentType(APPLICATION_JSON)
            .body(generateBillRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Bill>()
            .returnResult()

        val generatedBill = billResponse.responseBody
        assertNotNull(generatedBill)
        assertNotNull(generatedBill.id)
        assertEquals(createdPatient.id, generatedBill.patientId)
        assertEquals(createdDoctor.id, generatedBill.doctorId)
        assertEquals(BigDecimal("1000.00"), generatedBill.consultationCharge)
        assertEquals(5, generatedBill.discountPercentage)
        assertEquals(BigDecimal("50.00"), generatedBill.discountAmount)
        assertEquals(BigDecimal("950.00"), generatedBill.amountAfterDiscount)
        assertEquals(12, generatedBill.taxPercentage)
        assertEquals(BigDecimal("114.00"), generatedBill.taxAmount)
        assertEquals(BigDecimal("1064.00"), generatedBill.finalAmount)
        assertNotNull(generatedBill.billingDate)
    }
}

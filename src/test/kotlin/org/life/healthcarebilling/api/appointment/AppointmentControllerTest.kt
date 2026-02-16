package org.life.healthcarebilling.api.appointment

import org.life.healthcarebilling.api.createAppointmentRequest
import org.life.healthcarebilling.doctor
import org.life.healthcarebilling.domain.appointment.Appointment
import org.life.healthcarebilling.domain.appointment.AppointmentStatus
import org.life.healthcarebilling.patient1
import org.life.healthcarebilling.patient2
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import java.util.UUID
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class AppointmentControllerTest(@Autowired private val restTestClient: RestTestClient) {

    @LocalServerPort
    private val port = 0


    @Test
    fun `should create appointment with SCHEDULED status by default`() {
        val patientId = patient1.id
        val doctorId = doctor.id

        val request = """
        {
            "patientId": "$patientId",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-02-20T14:00:00"
        }
        """.trimIndent()

        val response = restTestClient.post()
            .uri("/appointments")
            .contentType(APPLICATION_JSON)
            .body(request)
            .exchange()
            .expectStatus().isOk
            .expectBody<Appointment>()
            .returnResult()

        val createdAppointment = response.responseBody
        assertNotNull(createdAppointment)
        assertEquals(AppointmentStatus.SCHEDULED, createdAppointment.status)
    }

    @Test
    fun `should create appointment for different patients and doctors`() {
        val patient1Id = patient1.id
        val patient2Id = patient2.id
        val doctorId = doctor.id

        val request = """
        {
            "patientId": "$patient1Id",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-02-25T09:00:00"
        }
        """.trimIndent()

        val response1 = restTestClient.post()
            .uri("/appointments")
            .contentType(APPLICATION_JSON)
            .body(request)
            .exchange()
            .expectStatus().isOk
            .expectBody<Appointment>()
            .returnResult()


        val request2 = """
        {
            "patientId": "$patient2Id",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-02-25T10:00:00"
        }
        """.trimIndent()

        val response2 = restTestClient.post()
            .uri("/appointments")
            .contentType(APPLICATION_JSON)
            .body(request2)
            .exchange()
            .expectStatus().isOk
            .expectBody<Appointment>()
            .returnResult()

        val appointment1 = response1.responseBody
        val appointment2 = response2.responseBody

        assertNotNull(appointment1)
        assertNotNull(appointment2)
        assertEquals(patient1Id, appointment1.patientId)
        assertEquals(patient2Id, appointment2.patientId)
        assertEquals(doctorId, appointment1.doctorId)
        assertEquals(doctorId, appointment2.doctorId)
    }

    @Test
    fun `should update appointment status to COMPLETED`() {

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
        assertEquals(createdAppointment.id, updatedAppointment.id)
        assertEquals(AppointmentStatus.COMPLETED, updatedAppointment.status)
    }

    @Test
    fun `should update appointment status to CANCELLED`() {

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

        val updateRequest = """
        {
            "status": "CANCELLED"
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
        assertEquals(AppointmentStatus.CANCELLED, updatedAppointment.status)
    }

    @Test
    fun `should return error when updating non-existent appointment`() {
        val nonExistentId = UUID.randomUUID()

        val updateRequest = """
        {
            "status": "COMPLETED"
        }
        """.trimIndent()

        restTestClient.patch()
            .uri("/appointments/$nonExistentId/status")
            .contentType(APPLICATION_JSON)
            .body(updateRequest)
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    fun `should return 400 when appointmentDateTime is in the past`() {
        val patientId = patient1.id
        val doctorId = doctor.id

        val request = """
        {
            "patientId": "$patientId",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-01-15T10:00:00"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/appointments")
            .contentType(APPLICATION_JSON)
            .body(request)
            .exchange()
            .expectStatus().isBadRequest
    }

}

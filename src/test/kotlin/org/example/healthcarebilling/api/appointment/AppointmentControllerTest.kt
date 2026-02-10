package org.example.healthcarebilling.api.appointment

import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentStatus
import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.patient.Patient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class AppointmentControllerTest(@Autowired private val restTestClient: RestTestClient) {

    @LocalServerPort
    private val port = 0


    @Test
    fun `should create appointment with SCHEDULED status by default`() {
        val patientId = Patient(firstName = "John", lastName = "Doe", dateOfBirth = LocalDate.of(1990, 6, 15)).id
        val doctorId = Doctor(
            firstName = "Jane",
            lastName = "Smith",
            npiNumber = "1234567890",
            specialty = "Cardiology",
            practiceStartDate = LocalDate.of(2015, 6, 15)
        ).id

        val request = """
        {
            "patientId": "$patientId",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-02-20T14:00:00"
        }
        """.trimIndent()

        val response = restTestClient.post()
            .uri("/appointments")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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
        val patient1Id = Patient(firstName = "John", lastName = "Doe", dateOfBirth = LocalDate.of(1990, 6, 15)).id
        val patient2Id = Patient(firstName = "Bruce", lastName = "Wayne", dateOfBirth = LocalDate.of(1990, 6, 15)).id
        val doctorId = Doctor(
            firstName = "Jane",
            lastName = "Smith",
            npiNumber = "1234567890",
            specialty = "Cardiology",
            practiceStartDate = LocalDate.of(2015, 6, 15)
        ).id

        val request = """
        {
            "patientId": "$patient1Id",
            "doctorId": "$doctorId",
            "appointmentDateTime": "2026-02-25T09:00:00"
        }
        """.trimIndent()

        val response1 = restTestClient.post()
            .uri("/appointments")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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
}

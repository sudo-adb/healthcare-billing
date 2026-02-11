package org.example.healthcarebilling.api.patient

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
import kotlin.test.assertEquals


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class PatientControllerTest(@Autowired private val restTestClient: RestTestClient) {

    @LocalServerPort
    private val port = 0


    @Test
    fun `should create a new patient`() {
        val jsonRequest = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "dateOfBirth": "1990-01-01"
        }
    """.trimIndent()

        val response = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = response.responseBody
        assertNotNull(createdPatient)
        assertEquals("John", createdPatient.firstName)
        assertEquals("Doe", createdPatient.lastName)
        assertEquals("1990-01-01", createdPatient.dateOfBirth.toString())
    }

    @Test
    fun `should get patient by firstName lastName and dateOfBirth`() {
        val jsonRequest = """
        {
            "firstName": "Bruce",
            "lastName": "Wayne",
            "dateOfBirth": "1990-06-15"
        }
    """.trimIndent()

        restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isOk


        val response = restTestClient.get()
            .uri("/patients?firstName=Bruce&lastName=Wayne&dateOfBirth=1990-06-15")
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val foundPatient = response.responseBody
        assertNotNull(foundPatient)
        assertEquals("Bruce", foundPatient.firstName)
        assertEquals("Wayne", foundPatient.lastName)
        assertEquals("1990-06-15", foundPatient.dateOfBirth.toString())
    }

    @Test
    fun `should return 404 when patient not found`() {
        restTestClient.get()
            .uri("/patients?firstName=Nobody&lastName=Whoever&dateOfBirth=2000-01-01")
            .exchange()
            .expectStatus().isNotFound
    }

}
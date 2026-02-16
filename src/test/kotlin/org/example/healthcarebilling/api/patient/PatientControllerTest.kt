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

    @Test
    fun `should return 400 when firstName is blank`() {
        val jsonRequest = """
        {
            "firstName": "",
            "lastName": "Doe",
            "dateOfBirth": "1990-01-01"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when lastName is blank`() {
        val jsonRequest = """
        {
            "firstName": "John",
            "lastName": "",
            "dateOfBirth": "1990-01-01"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when dateOfBirth is in the future`() {
        val jsonRequest = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "dateOfBirth": "2027-01-01"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should add insurance to patient`() {
        val createPatientRequest = """
        {
            "firstName": "Will",
            "lastName": "Gill",
            "dateOfBirth": "1985-03-15"
        }
        """.trimIndent()

        val createResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = createResponse.responseBody
        assertNotNull(createdPatient)

        val addInsuranceRequest = """
        {
            "binNumber": "123456",
            "pcnNumber": "PCN789"
        }
        """.trimIndent()

        val insuranceResponse = restTestClient.patch()
            .uri("/patients/${createdPatient.id}/insurance")
            .contentType(APPLICATION_JSON)
            .body(addInsuranceRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val updatedPatient = insuranceResponse.responseBody
        assertNotNull(updatedPatient)
        assertNotNull(updatedPatient.insurance)
        assertEquals("123456", updatedPatient.insurance?.binNumber)
        assertEquals("PCN789", updatedPatient.insurance?.pcnNumber)
        assertEquals(createdPatient.id, updatedPatient.insurance?.memberId)
    }

    @Test
    fun `should return 400 when binNumber is blank`() {
        val createPatientRequest = """
        {
            "firstName": "Tony",
            "lastName": "Stark",
            "dateOfBirth": "1980-01-01"
        }
        """.trimIndent()

        val createResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = createResponse.responseBody
        assertNotNull(createdPatient)

        val addInsuranceRequest = """
        {
            "binNumber": "",
            "pcnNumber": "PCN123"
        }
        """.trimIndent()

        restTestClient.patch()
            .uri("/patients/${createdPatient.id}/insurance")
            .contentType(APPLICATION_JSON)
            .body(addInsuranceRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when pcnNumber is blank`() {
        val createPatientRequest = """
        {
            "firstName": "Peter",
            "lastName": "Parker",
            "dateOfBirth": "1990-01-01"
        }
        """.trimIndent()

        val createResponse = restTestClient.post()
            .uri("/patients")
            .contentType(APPLICATION_JSON)
            .body(createPatientRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Patient>()
            .returnResult()

        val createdPatient = createResponse.responseBody
        assertNotNull(createdPatient)

        val addInsuranceRequest = """
        {
            "binNumber": "BIN456",
            "pcnNumber": ""
        }
        """.trimIndent()

        restTestClient.patch()
            .uri("/patients/${createdPatient.id}/insurance")
            .contentType(APPLICATION_JSON)
            .body(addInsuranceRequest)
            .exchange()
            .expectStatus().isBadRequest
    }
}
package org.example.healthcarebilling.api.doctor

import org.example.healthcarebilling.domain.doctor.Doctor
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
class DoctorControllerTest(@Autowired private val restTestClient: RestTestClient) {

    @LocalServerPort
    private val port = 0

    @Test
    fun `should create a new doctor`() {
        val jsonRequest = """
        {
            "firstName": "Jane",
            "lastName": "Smith",
            "npiNumber": "1234567890",
            "specialty": "Cardiology",
            "practiceStartDate": "2015-06-15"
        }
        """.trimIndent()

        val response = restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<Doctor>()
            .returnResult()

        val createdDoctor = response.responseBody
        assertNotNull(createdDoctor)
        assertEquals("Jane", createdDoctor.firstName)
        assertEquals("Smith", createdDoctor.lastName)
        assertEquals("1234567890", createdDoctor.npiNumber)
        assertEquals("Cardiology", createdDoctor.specialty)
        assertEquals("2015-06-15", createdDoctor.practiceStartDate.toString())
    }

    @Test
    fun `should return 400 when firstName is blank`() {
        val jsonRequest = """
        {
            "firstName": "",
            "lastName": "Smith",
            "npiNumber": "1234567890",
            "specialty": "Cardiology",
            "practiceStartDate": "2015-06-15"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when lastName is blank`() {
        val jsonRequest = """
        {
            "firstName": "Jane",
            "lastName": "",
            "npiNumber": "1234567890",
            "specialty": "Cardiology",
            "practiceStartDate": "2015-06-15"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when npiNumber is blank`() {
        val jsonRequest = """
        {
            "firstName": "Jane",
            "lastName": "Smith",
            "npiNumber": "",
            "specialty": "Cardiology",
            "practiceStartDate": "2015-06-15"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return 400 when practiceStartDate is in the future`() {
        val jsonRequest = """
        {
            "firstName": "Jane",
            "lastName": "Smith",
            "npiNumber": "1234567890",
            "specialty": "Cardiology",
            "practiceStartDate": "2027-01-01"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should find doctor by NPI number`() {

        val jsonRequest = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "npiNumber": "9876543210",
            "specialty": "Neurology",
            "practiceStartDate": "2010-03-20"
        }
        """.trimIndent()

        restTestClient.post()
            .uri("/doctors")
            .contentType(APPLICATION_JSON)
            .body(jsonRequest)
            .exchange()
            .expectStatus().isOk


        val response = restTestClient.get()
            .uri("/doctors?npiNumber=9876543210")
            .exchange()
            .expectStatus().isOk
            .expectBody<Doctor>()
            .returnResult()

        val foundDoctor = response.responseBody
        assertNotNull(foundDoctor)
        assertEquals("John", foundDoctor.firstName)
        assertEquals("Doe", foundDoctor.lastName)
        assertEquals("9876543210", foundDoctor.npiNumber)
        assertEquals("Neurology", foundDoctor.specialty)
        assertEquals("2010-03-20", foundDoctor.practiceStartDate.toString())
    }

    @Test
    fun `should return 404 when doctor not found by NPI number`() {
        restTestClient.get()
            .uri("/doctors?npiNumber=0000000000")
            .exchange()
            .expectStatus().isNotFound
    }
}

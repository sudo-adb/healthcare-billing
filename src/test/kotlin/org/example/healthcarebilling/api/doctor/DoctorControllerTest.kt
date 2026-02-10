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
}

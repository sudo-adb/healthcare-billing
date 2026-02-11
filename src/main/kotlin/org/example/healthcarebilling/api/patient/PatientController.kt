package org.example.healthcarebilling.api.patient

import org.example.healthcarebilling.domain.patient.CreatePatientUseCase
import org.example.healthcarebilling.domain.patient.GetPatientUseCase
import org.example.healthcarebilling.domain.patient.Patient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class PatientController @Autowired constructor(
    private val createPatientUseCase: CreatePatientUseCase,
    private val getPatientUseCase: GetPatientUseCase
) {

    @PostMapping("/patients")
    fun createPatient(@RequestBody patient: CreatePatientRequest): Patient {
        return createPatientUseCase(
            patient.firstName,
            patient.lastName,
            patient.dateOfBirth.toString()
        )
    }

    @GetMapping("/patients")
    fun getPatient(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam dateOfBirth: LocalDate
    ): ResponseEntity<Patient> {
        val patient = getPatientUseCase(firstName, lastName, dateOfBirth)
        return if (patient != null) {
            ResponseEntity.ok(patient)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
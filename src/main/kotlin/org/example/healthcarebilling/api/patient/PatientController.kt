package org.example.healthcarebilling.api.patient

import jakarta.validation.Valid
import org.example.healthcarebilling.domain.patient.CreatePatientUseCase
import org.example.healthcarebilling.domain.patient.GetPatientUseCase
import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.insurance.AddPatientInsuranceUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@RestController
class PatientController @Autowired constructor(
    private val createPatientUseCase: CreatePatientUseCase,
    private val getPatientUseCase: GetPatientUseCase,
    private val addPatientInsuranceUseCase: AddPatientInsuranceUseCase
) {

    @PostMapping("/patients")
    fun createPatient(@Valid @RequestBody patient: CreatePatientRequest): Patient {
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

    @PatchMapping("/patients/{id}/insurance")
    fun addInsuranceToPatient(
        @PathVariable id: UUID,
        @Valid @RequestBody request: AddPatientInsuranceRequest
    ): Patient {
        return addPatientInsuranceUseCase(id, request.binNumber, request.pcnNumber)
    }
}
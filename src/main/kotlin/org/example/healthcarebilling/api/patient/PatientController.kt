package org.example.healthcarebilling.api.patient

import org.example.healthcarebilling.domain.patient.CreatePatientUseCase
import org.example.healthcarebilling.domain.patient.Patient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PatientController @Autowired constructor(private val createPatientUseCase: CreatePatientUseCase) {

    @PostMapping("/patients")
    fun createPatient(@RequestBody patient: CreatePatientRequest): Patient {
        return createPatientUseCase(
            patient.firstName,
            patient.lastName,
            patient.dateOfBirth.toString()
        )
    }
}
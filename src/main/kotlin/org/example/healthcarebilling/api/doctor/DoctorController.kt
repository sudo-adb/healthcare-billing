package org.example.healthcarebilling.api.doctor

import jakarta.validation.Valid
import org.example.healthcarebilling.domain.doctor.CreateDoctorUseCase
import org.example.healthcarebilling.domain.doctor.Doctor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DoctorController @Autowired constructor(private val createDoctorUseCase: CreateDoctorUseCase) {

    @PostMapping("/doctors")
    fun createDoctor(@Valid @RequestBody request: CreateDoctorRequest): Doctor {
        return createDoctorUseCase(
            request.firstName,
            request.lastName,
            request.npiNumber,
            request.specialty,
            request.practiceStartDate.toString()
        )
    }
}

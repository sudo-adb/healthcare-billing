package org.example.healthcarebilling.api.doctor

import jakarta.validation.Valid
import org.example.healthcarebilling.domain.doctor.CreateDoctorUseCase
import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.doctor.GetDoctorByNpiNumberUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class DoctorController @Autowired constructor(
    private val createDoctorUseCase: CreateDoctorUseCase,
    private val getDoctorByNpiNumberUseCase: GetDoctorByNpiNumberUseCase
) {

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

    @GetMapping("/doctors")
    fun getDoctorByNpiNumber(@RequestParam npiNumber: String): ResponseEntity<Doctor> {
        val doctor = getDoctorByNpiNumberUseCase(npiNumber)
        return if (doctor != null) {
            ResponseEntity.ok(doctor)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

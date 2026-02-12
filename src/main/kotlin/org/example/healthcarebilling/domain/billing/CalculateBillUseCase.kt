package org.example.healthcarebilling.domain.billing

import org.example.healthcarebilling.domain.doctor.DoctorRepository
import org.example.healthcarebilling.domain.doctor.GetConsultationChargeUseCase
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class CalculateBillUseCase(
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val getConsultationChargeUseCase: GetConsultationChargeUseCase,
    private val getDiscountUseCase: GetDiscountUseCase
) {

    operator fun invoke(patientId: UUID, doctorId: UUID): Bill {
        val patient = patientRepository.findById(patientId)
            ?: throw IllegalArgumentException("Patient with id $patientId not found")

        val doctor = doctorRepository.findById(doctorId)
            ?: throw IllegalArgumentException("Doctor with id $doctorId not found")

        val consultationCharge = getConsultationChargeUseCase(
            doctor.specialty,
            doctor.yearsOfExperience
        )

        val discountPercentage = getDiscountUseCase(patient.id)

        val bill = Bill(
            patientId = patient.id,
            doctorId = doctor.id,
            consultationCharge = consultationCharge,
            discountPercentage = discountPercentage
        )

        return bill
    }
}

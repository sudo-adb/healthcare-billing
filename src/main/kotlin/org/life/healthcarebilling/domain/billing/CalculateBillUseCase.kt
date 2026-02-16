package org.life.healthcarebilling.domain.billing

import org.life.healthcarebilling.domain.doctor.DoctorRepository
import org.life.healthcarebilling.domain.doctor.GetConsultationChargeUseCase
import org.life.healthcarebilling.domain.patient.PatientRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class CalculateBillUseCase(
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val getConsultationChargeUseCase: GetConsultationChargeUseCase,
    private val getDiscountUseCase: GetDiscountUseCase,
    private val getTaxUseCase: GetTaxUseCase,
    private val getCopayUseCase: GetCopayUseCase
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
        val taxPercentage = getTaxUseCase()
        val copayPercentage =  getCopayUseCase()

        val bill = Bill(
            patientId = patient.id,
            doctorId = doctor.id,
            consultationCharge = consultationCharge,
            discountPercentage = discountPercentage,
            taxPercentage = taxPercentage,
            copayPercentage = copayPercentage
        )

        return bill
    }
}

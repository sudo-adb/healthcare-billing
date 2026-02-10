package org.example.healthcarebilling.data.patient

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.example.healthcarebilling.domain.patient.insurance.Insurance
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryPatientRepository : PatientRepository {

    private val patients = mutableListOf<Patient>()

    override fun save(patient: Patient): Patient {
        patients.add(patient)
        return patient
    }

    override fun addInsuranceToPatient(patientId: UUID, insurance: Insurance): Patient {
        val patient = findPatientById(patientId)
        patient.insurance = insurance
        return patient
    }

    private fun findPatientById(patientId: UUID): Patient {
        val patient = patients.find { it.id == patientId }
            ?: throw IllegalArgumentException("Patient with id $patientId not found")
        return patient
    }
}
package org.example.healthcarebilling.data.patient

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.example.healthcarebilling.domain.patient.insurance.Insurance
import java.time.LocalDate
import java.util.*

class InMemoryPatientRepository : PatientRepository {

    private val patients = mutableListOf<Patient>()

    override fun save(patient: Patient): Patient {
        patients.add(patient)
        return patient
    }

    override fun addInsuranceToPatient(patientId: UUID, insurance: Insurance): Patient {
        val patient = findById(patientId) ?: throw IllegalArgumentException("Patient with id $patientId not found")
        patient.insurance = insurance
        return patient
    }

    override fun findByFirstNameLastNameAndDateOfBirth(
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate
    ): Patient? {
        return patients.find {
            it.firstName == firstName && it.lastName == lastName && it.dateOfBirth == dateOfBirth
        }
    }

    override fun findById(patientId: UUID): Patient? {
        return patients.find { it.id == patientId }
    }
}


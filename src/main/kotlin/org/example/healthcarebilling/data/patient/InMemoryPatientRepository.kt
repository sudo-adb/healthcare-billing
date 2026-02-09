package org.example.healthcarebilling.data.patient

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryPatientRepository : PatientRepository {

    private val patients = mutableListOf<Patient>()

    override fun save(patient: Patient): Patient {
        patients.add(patient)
        return patient
    }
}
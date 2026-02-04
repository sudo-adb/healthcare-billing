package org.example.healthcarebilling.domain.patient

interface PatientRepository {
    fun save(patient: Patient): Patient
}

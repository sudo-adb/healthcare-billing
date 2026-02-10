package org.example.healthcarebilling.domain.patient

import org.example.healthcarebilling.domain.patient.insurance.Insurance
import java.util.UUID

interface PatientRepository {
    fun save(patient: Patient): Patient
    fun addInsuranceToPatient(patientId: UUID, insurance: Insurance): Patient
}

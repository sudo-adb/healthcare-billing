package org.life.healthcarebilling.domain.patient

import org.life.healthcarebilling.domain.patient.insurance.Insurance
import java.time.LocalDate
import java.util.UUID

interface PatientRepository {
    fun save(patient: Patient): Patient
    fun addInsuranceToPatient(patientId: UUID, insurance: Insurance): Patient
    fun findByFirstNameLastNameAndDateOfBirth(firstName: String, lastName: String, dateOfBirth: LocalDate): Patient?
    fun findById(patientId: UUID): Patient?
}

package org.example.healthcarebilling.data.patient

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.example.healthcarebilling.domain.patient.insurance.Insurance
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
@Primary
class PatientRepositoryImpl(
    private val jpaPatientRepository: JpaPatientRepository
) : PatientRepository {

    override fun save(patient: Patient): Patient {
        val entity = PatientEntity.fromDomain(patient)
        val savedEntity = jpaPatientRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun addInsuranceToPatient(patientId: UUID, insurance: Insurance): Patient {
        val entity = jpaPatientRepository.findById(patientId).orElseThrow {
            IllegalArgumentException("Patient with id $patientId not found")
        }

        val patient = entity.toDomain()
        patient.insurance = insurance

        val updatedEntity = PatientEntity.fromDomain(patient)
        val savedEntity = jpaPatientRepository.save(updatedEntity)
        return savedEntity.toDomain()
    }

    override fun findByFirstNameLastNameAndDateOfBirth(
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate
    ): Patient? {
        return jpaPatientRepository.findByFirstNameAndLastNameAndDateOfBirth(
            firstName,
            lastName,
            dateOfBirth
        )?.toDomain()
    }

    override fun findById(patientId: UUID): Patient? {
        return jpaPatientRepository.findById(patientId).map { it.toDomain() }.orElse(null)
    }
}


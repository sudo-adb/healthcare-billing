package org.example.healthcarebilling.data.doctor

import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.doctor.DoctorRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@Primary
class DoctorRepositoryImpl(
    private val jpaDoctorRepository: JpaDoctorRepository
) : DoctorRepository {

    override fun save(doctor: Doctor): Doctor {
        val entity = DoctorEntity.fromDomain(doctor)
        val savedEntity = jpaDoctorRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: UUID): Doctor? {
        return jpaDoctorRepository.findById(id).map { it.toDomain() }.orElse(null)
    }
}


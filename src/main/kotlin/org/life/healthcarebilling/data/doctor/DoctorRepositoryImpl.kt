package org.life.healthcarebilling.data.doctor

import org.life.healthcarebilling.domain.doctor.Doctor
import org.life.healthcarebilling.domain.doctor.DoctorRepository
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

    override fun findByNpiNumber(npiNumber: String): Doctor? {
        return jpaDoctorRepository.findByNpiNumber(npiNumber)?.toDomain()
    }
}


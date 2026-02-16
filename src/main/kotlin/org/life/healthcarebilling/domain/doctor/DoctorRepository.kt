package org.life.healthcarebilling.domain.doctor

import java.util.UUID

interface DoctorRepository {
    fun save(doctor: Doctor): Doctor
    fun findById(id: UUID): Doctor?
    fun findByNpiNumber(npiNumber: String): Doctor?
}

package org.example.healthcarebilling.domain.doctor

import java.util.UUID

interface DoctorRepository {
    fun save(doctor: Doctor): Doctor
    fun findById(id: UUID): Doctor?
}

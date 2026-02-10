package org.example.healthcarebilling.domain.doctor

interface DoctorRepository {
    fun save(doctor: Doctor): Doctor
}

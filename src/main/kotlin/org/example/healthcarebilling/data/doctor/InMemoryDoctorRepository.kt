package org.example.healthcarebilling.data.doctor

import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.doctor.DoctorRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryDoctorRepository : DoctorRepository {

    private val doctors = mutableListOf<Doctor>()

    override fun save(doctor: Doctor): Doctor {
        doctors.add(doctor)
        return doctor
    }
}

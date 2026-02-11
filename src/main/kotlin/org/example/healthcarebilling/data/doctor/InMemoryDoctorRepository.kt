package org.example.healthcarebilling.data.doctor

import org.example.healthcarebilling.domain.doctor.Doctor
import org.example.healthcarebilling.domain.doctor.DoctorRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryDoctorRepository : DoctorRepository {

    private val doctors = mutableListOf<Doctor>()

    override fun save(doctor: Doctor): Doctor {
        doctors.add(doctor)
        return doctor
    }

    override fun findById(id: UUID): Doctor? {
        return doctors.find { it.id == id }
    }
}

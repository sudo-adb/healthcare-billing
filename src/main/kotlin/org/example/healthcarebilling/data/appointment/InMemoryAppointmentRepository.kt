package org.example.healthcarebilling.data.appointment

import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryAppointmentRepository : AppointmentRepository {

    private val appointments = mutableListOf<Appointment>()

    override fun save(appointment: Appointment): Appointment {
        appointments.add(appointment)
        return appointment
    }
}

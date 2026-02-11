package org.example.healthcarebilling.data.appointment

import org.example.healthcarebilling.domain.appointment.Appointment
import org.example.healthcarebilling.domain.appointment.AppointmentRepository
import org.example.healthcarebilling.domain.appointment.AppointmentStatus
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class InMemoryAppointmentRepository : AppointmentRepository {

    private val appointments = mutableListOf<Appointment>()

    override fun save(appointment: Appointment): Appointment {
        appointments.add(appointment)
        return appointment
    }

    override fun countCompletedByPatientId(patientId: UUID): Int {
        return appointments.count {
            it.patientId == patientId && it.status == AppointmentStatus.COMPLETED
        }
    }
}

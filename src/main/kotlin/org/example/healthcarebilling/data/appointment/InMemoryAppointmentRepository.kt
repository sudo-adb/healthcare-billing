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

    override fun findById(id: UUID): Appointment? {
        return appointments.firstOrNull { it.id == id }
    }

    override fun update(
        appointment: Appointment,
        status: AppointmentStatus
    ): Appointment {
        val index = appointments.indexOfFirst { it.id == appointment.id }
        if (index == -1) {
            throw IllegalArgumentException("Appointment with id ${appointment.id} not found")
        }
        val updatedAppointment = appointment.copy(status = status)
        appointments[index] = updatedAppointment
        return updatedAppointment
    }
}

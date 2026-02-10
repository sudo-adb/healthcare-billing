package org.example.healthcarebilling.domain.appointment

interface AppointmentRepository {
    fun save(appointment: Appointment): Appointment
}

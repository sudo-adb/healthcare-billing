package org.example.healthcarebilling.domain.appointment

import java.util.UUID

interface AppointmentRepository {
    fun save(appointment: Appointment): Appointment
    fun countCompletedByPatientId(patientId: UUID): Int
}

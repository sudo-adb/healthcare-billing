package org.life.healthcarebilling.domain.appointment

import java.util.UUID

interface AppointmentRepository {
    fun save(appointment: Appointment): Appointment
    fun countCompletedByPatientId(patientId: UUID): Int
    fun findById(id: UUID): Appointment?
    fun update(appointment: Appointment, status: AppointmentStatus): Appointment
}

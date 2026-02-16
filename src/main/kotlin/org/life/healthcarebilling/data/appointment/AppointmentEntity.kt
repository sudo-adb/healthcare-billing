package org.life.healthcarebilling.data.appointment

import jakarta.persistence.*
import org.life.healthcarebilling.domain.appointment.Appointment
import org.life.healthcarebilling.domain.appointment.AppointmentStatus
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "appointment")
data class AppointmentEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val patientId: UUID,
    val doctorId: UUID,
    val appointmentTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: AppointmentStatus
) {
    fun toDomain(): Appointment {
        return Appointment(
            id = id,
            patientId = patientId,
            doctorId = doctorId,
            appointmentTime = appointmentTime,
            status = status
        )
    }

    companion object {
        fun fromDomain(appointment: Appointment): AppointmentEntity {
            return AppointmentEntity(
                id = appointment.id,
                patientId = appointment.patientId,
                doctorId = appointment.doctorId,
                appointmentTime = appointment.appointmentTime,
                status = appointment.status
            )
        }
    }
}



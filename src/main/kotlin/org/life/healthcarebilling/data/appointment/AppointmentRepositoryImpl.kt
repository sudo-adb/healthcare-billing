package org.life.healthcarebilling.data.appointment

import org.life.healthcarebilling.domain.appointment.Appointment
import org.life.healthcarebilling.domain.appointment.AppointmentRepository
import org.life.healthcarebilling.domain.appointment.AppointmentStatus
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@Primary
class AppointmentRepositoryImpl(
    private val jpaAppointmentRepository: JpaAppointmentRepository
) : AppointmentRepository {

    override fun save(appointment: Appointment): Appointment {
        val entity = AppointmentEntity.fromDomain(appointment)
        val savedEntity = jpaAppointmentRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun countCompletedByPatientId(patientId: UUID): Int {
        return jpaAppointmentRepository.countByPatientIdAndStatus(patientId, AppointmentStatus.COMPLETED)
    }

    override fun findById(id: UUID): Appointment? {
        return jpaAppointmentRepository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun update(appointment: Appointment, status: AppointmentStatus): Appointment {
        val entity = jpaAppointmentRepository.findById(appointment.id).orElseThrow {
            IllegalArgumentException("Appointment with id ${appointment.id} not found")
        }
        entity.status = status
        val savedEntity = jpaAppointmentRepository.save(entity)
        return savedEntity.toDomain()
    }
}


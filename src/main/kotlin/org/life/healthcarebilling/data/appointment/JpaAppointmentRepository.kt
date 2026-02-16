package org.life.healthcarebilling.data.appointment

import org.life.healthcarebilling.domain.appointment.AppointmentStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaAppointmentRepository : JpaRepository<AppointmentEntity, UUID> {
    fun countByPatientIdAndStatus(patientId: UUID, status: AppointmentStatus): Int
}


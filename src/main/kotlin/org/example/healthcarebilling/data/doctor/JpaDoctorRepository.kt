package org.example.healthcarebilling.data.doctor

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaDoctorRepository : JpaRepository<DoctorEntity, UUID>


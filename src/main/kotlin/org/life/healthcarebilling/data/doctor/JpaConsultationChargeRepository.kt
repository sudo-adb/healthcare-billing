package org.life.healthcarebilling.data.doctor

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaConsultationChargeRepository : JpaRepository<ConsultationChargeEntity, Long> {

    @Query("""
        SELECT c FROM ConsultationChargeEntity c 
        WHERE LOWER(c.specialty) = LOWER(:specialty) 
        AND c.minYearsExperience <= :yearsOfExperience 
        AND (c.maxYearsExperience IS NULL OR c.maxYearsExperience >= :yearsOfExperience)
    """)
    fun findBySpecialtyAndExperience(
        @Param("specialty") specialty: String,
        @Param("yearsOfExperience") yearsOfExperience: Int
    ): List<ConsultationChargeEntity>
}


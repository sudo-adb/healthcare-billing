package org.example.healthcarebilling.data.patient

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.healthcarebilling.domain.patient.insurance.Insurance
import java.util.UUID

@Entity
@Table(name = "insurance")
data class InsuranceEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val binNumber: String,
    val pcnNumber: String,
    val memberId: UUID
) {
    fun toDomain(): Insurance {
        return Insurance(
            id = id,
            binNumber = binNumber,
            pcnNumber = pcnNumber,
            memberId = memberId
        )
    }

    companion object {
        fun fromDomain(insurance: Insurance): InsuranceEntity {
            return InsuranceEntity(
                id = insurance.id,
                binNumber = insurance.binNumber,
                pcnNumber = insurance.pcnNumber,
                memberId = insurance.memberId
            )
        }
    }
}


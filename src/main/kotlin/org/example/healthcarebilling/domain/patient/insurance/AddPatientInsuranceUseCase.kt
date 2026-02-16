package org.example.healthcarebilling.domain.patient.insurance

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AddPatientInsuranceUseCase(val patientRepository: PatientRepository) {

    operator fun invoke(patientId: UUID, binNumber: String, pcnNumber: String): Patient {
        val insurance = Insurance(patientId, binNumber, pcnNumber)
        return patientRepository.addInsuranceToPatient(patientId,insurance)

    }

}

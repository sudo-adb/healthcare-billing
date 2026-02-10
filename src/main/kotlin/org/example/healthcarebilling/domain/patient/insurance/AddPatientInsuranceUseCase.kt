package org.example.healthcarebilling.domain.patient.insurance

import org.example.healthcarebilling.domain.patient.Patient
import org.example.healthcarebilling.domain.patient.PatientRepository
import java.util.UUID

class AddPatientInsuranceUseCase(val patientRepository: PatientRepository) {

    operator fun invoke(patientId: UUID, binNumber: String, pcnNumber: String): Patient {
        val insurance = Insurance(patientId, binNumber, pcnNumber)
        return patientRepository.addInsuranceToPatient(patientId,insurance)

    }

}

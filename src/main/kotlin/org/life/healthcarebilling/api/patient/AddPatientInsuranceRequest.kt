package org.life.healthcarebilling.api.patient

import jakarta.validation.constraints.NotBlank

data class AddPatientInsuranceRequest(
    @NotBlank(message = "BIN number cannot be blank")
    val binNumber: String,
    @NotBlank(message = "PCN number cannot be blank")
    val pcnNumber: String
)


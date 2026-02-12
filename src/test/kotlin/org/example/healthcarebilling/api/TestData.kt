package org.example.healthcarebilling.api

import org.example.healthcarebilling.doctor
import org.example.healthcarebilling.patient1

val createPatientRequest = """
        {
            "firstName": "${patient1.firstName}",
            "lastName": "${patient1.lastName}",
            "dateOfBirth": "${patient1.dateOfBirth}"
        }
        """.trimIndent()

val createDoctorRequest = """
        {
            "firstName": "${doctor.firstName}",
            "lastName": "${doctor.lastName}",
            "npiNumber": "${doctor.npiNumber}",
            "specialty": "${doctor.specialty}",
            "practiceStartDate": "${doctor.practiceStartDate}"
        }
        """.trimIndent()

val createAppointmentRequest = """
        {
            "patientId": "${patient1.id}",
            "doctorId": "${doctor.id}",
            "appointmentDateTime": "2026-03-01T11:00:00"
        }
        """.trimIndent()
# Healthcare Billing

Kotlin + Spring Boot REST API for managing patients, doctors, appointments, and generating bills.

Calculates bills based on  following rules:
1. Consultation charges of doctors are based on their specialization and years of experience.
2. Discounts are applied based on the patient's  number of previous completed appointments with any doctor (Max discount capped at 10%)
3. Tax is calculated at a flat rate of 12% on the total bill amount after applying discounts.

For example:
For a patient is visiting a Cardio Specialist with 15 years of exp and has had 5 previous appointments,
they would receive a 5% discount on the consultation charges. 
If the consultation charge is $1000, the discounted amount would be $950.
After applying the 12% tax, the final bill amount would be $1064.

### Endpoints

* `POST /patients` - Create a patient

* `GET /patients?firstName=...&lastName=...&dateOfBirth=YYYY-MM-DD` - Find a patient

* `POST /doctors` - Create a doctor

* `POST /appointments` - Create an appointment

* `PATCH /appointments/{id}/status` - Update appointment status

* `POST /bills` - Generate a bill for a patient + doctor

### Build and Run

`./gradlew test` : Runs all the tests

`./gradlew bootRun` : Run Application (default: http://localhost:8080)

`./gradlew build` : Build the jar


### Assumptions and Implementations
* The currency is assumed to be `$`, not mentioned or displayed explicitly in any response
* A specialization of type GENERAL with consultation charges is added to handle cases where doctor specialization is not specified or does not match predefined Specializations.
* The tax is calculated on the total bill amount after applying discounts, which means it includes the discounted consultation charges 
* For Bill generation, no prior/active scheduled appointment is required, as long as the patient and doctor exist in the system, a bill can be generated based on the consultation charges, discounts, and tax rules.
* By Default the appointment status is set to SCHEDULED when created, and it can be updated to COMPLETED or CANCELLED using the status update endpoint.
* While creating appoint, no validation is done to check if the patient already has an appointment with the same doctor at the same date and time.
* No Validation is done to check appoint date and time, it is assumed that the provided date and time are valid and in the future.

### TODO


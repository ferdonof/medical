--liquibase formatted sql
--changeset pmfernan@gmail.com:medical_appointments_0.0.1_0
--comment: CREATE appointment_slots table

CREATE TABLE appointment_slots (
                      id UUID PRIMARY KEY,
                      doctor_id UUID NOT NULL,
                      patient_id UUID,
                      specialty VARCHAR(50) NOT NULL,
                      appointment_at TIMESTAMPTZ(6) NOT NULL,
                      reserved_at TIMESTAMPTZ(6),
                      status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
                      created_at TIMESTAMPTZ(6) NOT NULL,
                      updated_at TIMESTAMPTZ(6) NOT NULL
);
CREATE INDEX idx_appointment_slots_appointment_doctor_specialty ON appointment_slots (appointment_at, doctor_id, specialty);
CREATE INDEX idx_appointment_slots_appointment_specialty_status ON appointment_slots (appointment_at, specialty, status) WHERE status IN ('AVAILABLE', 'CANCELLED');

--rollback DROP TABLE appointment_slots;
--rollback DROP INDEX idx_appointment_slots_appointment_doctor_specialty;
--rollback DROP INDEX idx_appointment_slots_appointment_specialty_status;
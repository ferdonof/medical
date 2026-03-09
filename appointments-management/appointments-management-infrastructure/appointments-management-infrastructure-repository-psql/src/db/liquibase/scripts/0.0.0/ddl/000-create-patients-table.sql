--liquibase formatted sql
--changeset pmfernan@gmail.com:medical_appointments_0.0.0_0
--comment: CREATE patient table

CREATE TABLE patients (
           id UUID PRIMARY KEY,
           name VARCHAR(100) NOT NULL,
           email VARCHAR(150) NOT NULL,
           phone VARCHAR(50) NOT NULL,
           created_at TIMESTAMPTZ(6) NOT NULL,
           updated_at TIMESTAMPTZ(6) NOT NULL
);

CREATE INDEX idx_patients_email ON patients (email);

--rollback DROP TABLE patient;
--rollback DROP INDEX idx_patients_email;


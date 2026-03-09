--liquibase formatted sql
--changeset pmfernan@gmail.com:medical_appointments_0.0.2_0
--comment: CREATE doctors table

CREATE TABLE doctors (
                      id UUID PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      specialty VARCHAR(50) NOT NULL,
                      created_at TIMESTAMPTZ(6) NOT NULL,
                      updated_at TIMESTAMPTZ(6) NOT NULL
);

--rollback DROP TABLE doctors;
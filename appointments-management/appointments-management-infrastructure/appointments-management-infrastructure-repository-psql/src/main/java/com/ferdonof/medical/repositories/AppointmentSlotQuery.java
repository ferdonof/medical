package com.ferdonof.medical.repositories;

import lombok.Getter;

@Getter
public enum AppointmentSlotQuery {

	RESERVE_FIRST_AVAILABLE_SLOT("""
				UPDATE appointment_slots
				SET patient_id = :patientId, reserved_at = NOW(), status = 'RESERVED'
				WHERE id = (
					SELECT id FROM appointment_slots
					WHERE
						specialty = :specialty
						AND status IN ('AVAILABLE', 'CANCELLED')
						AND (appointment_at > :appointmentAfter)
					ORDER BY appointment_at ASC
					LIMIT 1
				)
				RETURNING id, doctor_id, patient_id, specialty, appointment_at, reserved_at, created_at, updated_at, status;
			""");

	private final String sql;

	AppointmentSlotQuery(String sql) {
		this.sql = sql;
	}

}

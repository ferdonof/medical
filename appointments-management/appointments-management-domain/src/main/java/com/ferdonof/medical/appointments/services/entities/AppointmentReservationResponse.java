package com.ferdonof.medical.appointments.services.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentReservationResponse(UUID appointmentId, UUID patientId, UUID doctorId,
		LocalDateTime reservationDateTime) {
}

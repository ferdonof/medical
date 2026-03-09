package com.ferdonof.medical.appointments.services.entities;

import lombok.Builder;

@Builder
public record AppointmentReservationResponse(String appointmentId, String status) {
}

package com.ferdonof.medical.appointments.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ferdonof.medical.appointments.enums.SlotStatus;
import com.ferdonof.medical.commons.entities.Doctor;
import lombok.Builder;

@Builder(toBuilder = true)
public record AppointmentSlot(UUID id, Doctor doctor, LocalDateTime appointmentAt, LocalDateTime reservedAt,
		SlotStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
}

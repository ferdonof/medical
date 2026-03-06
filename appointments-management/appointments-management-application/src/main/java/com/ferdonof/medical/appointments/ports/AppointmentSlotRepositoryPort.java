package com.ferdonof.medical.appointments.ports;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.commons.enums.SpecialistType;

public interface AppointmentSlotRepositoryPort {
  Optional<AppointmentSlot> reserveFirstAvailableSlot(UUID patientId, SpecialistType specialistType, LocalDateTime appointmentAfter);
}

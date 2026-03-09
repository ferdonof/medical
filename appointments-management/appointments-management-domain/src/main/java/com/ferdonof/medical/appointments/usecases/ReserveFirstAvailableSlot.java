package com.ferdonof.medical.appointments.usecases;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.commons.enums.SpecialistType;

import java.util.Optional;
import java.util.UUID;

public interface ReserveFirstAvailableSlot {
	Optional<AppointmentSlot> execute(UUID patientId, SpecialistType specialistType);
}

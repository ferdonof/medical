package com.ferdonof.medical.appointments.usecases;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;

import java.util.Optional;

public interface ReserveFirstAvailableSlot {
	Optional<AppointmentSlot> execute(String symptoms);
}

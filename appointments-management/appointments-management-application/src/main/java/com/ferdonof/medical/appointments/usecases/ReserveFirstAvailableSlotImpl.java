package com.ferdonof.medical.appointments.usecases;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.commons.enums.SpecialistType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ReserveFirstAvailableSlotImpl implements ReserveFirstAvailableSlot {

	private final AppointmentSlotRepositoryPort appointmentSlotRepositoryPort;

	@Override
	public Optional<AppointmentSlot> execute(UUID patientId, SpecialistType specialistType) {
		return this.appointmentSlotRepositoryPort.reserveFirstAvailableSlot(patientId, specialistType,
				LocalDateTime.now().plusMinutes(90));
	}
}

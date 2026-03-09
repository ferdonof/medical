package com.ferdonof.medical.appointments.usecases;

import java.util.Objects;
import java.util.UUID;

import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CompleteAppointmentImpl implements CompleteAppointment {

	private final AppointmentSlotRepositoryPort appointmentSlotRepositoryPort;

	@Override
	public void execute(final UUID slotId) {
		Objects.requireNonNull(slotId, "slotId must not be null");
		log.info("Executing CompleteAppointment with slotId: {}", slotId);
		this.appointmentSlotRepositoryPort.completeAppointment(slotId);
	}
}

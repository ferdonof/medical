package com.ferdonof.medical.appointments.services;

import java.util.Objects;

import com.ferdonof.medical.appointments.ports.AppointmentConfirmationHandlerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AppointmentConfirmationServiceImpl implements AppointmentConfirmationService {

	private final AppointmentConfirmationHandlerPort appointmentConfirmationHandlerPort;

	@Override
	public void execute(final String processId) {
		Objects.requireNonNull(processId, "processId must not be null");
		this.appointmentConfirmationHandlerPort.confirmAppointment(processId);
	}
}

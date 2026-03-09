package com.ferdonof.medical.appointments.services;

import com.ferdonof.medical.appointments.ports.AppointmentConfirmationHandlerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppointmentConfirmationServiceTest {

	private final AppointmentConfirmationHandlerPort appointmentConfirmationPort = mock(
			AppointmentConfirmationHandlerPort.class);

	private final AppointmentConfirmationService useCase = new AppointmentConfirmationServiceImpl(
			this.appointmentConfirmationPort);

	@Test
	void execute_shouldCallConfirmAppointmentPort() {
		final String processId = "test-process-id";

		doNothing().when(this.appointmentConfirmationPort).confirmAppointment(processId);

		this.useCase.execute(processId);

		verify(this.appointmentConfirmationPort).confirmAppointment(processId);
	}
}
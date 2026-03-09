package com.ferdonof.medical.appointments.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ferdonof.medical.appointments.ports.AppointmentAttendPatientPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppointmentAttendPatientServiceTest {

	private final AppointmentAttendPatientPort attendPatientPort = mock(AppointmentAttendPatientPort.class);

	private final AppointmentAttendPatientService useCase = new AppointmentAttendPatientServiceImpl(
			this.attendPatientPort);

	@Test
	void execute_shouldCallAttendPatientPort() {
		final String processId = "test-process-id";

		doNothing().when(this.attendPatientPort).attendPatient(any());

		this.useCase.execute(processId);

		verify(this.attendPatientPort).attendPatient(processId);
	}

	@Test
	void execute_shouldThrowWhenProcessIdIsNull() {
		assertThatThrownBy(() -> this.useCase.execute(null)).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("processId must not be null");
	}
}
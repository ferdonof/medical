package com.ferdonof.medical.appointments.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.enums.ChestPainType;
import com.ferdonof.medical.appointments.enums.FatigueType;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessStarterPort;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;
import com.ferdonof.medical.commons.entities.ProcessResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppointmentReservationServiceTest {
	public static final UUID PATIENT_ID = UUID.randomUUID();
	private final AppointmentsProcessStarterPort processManager = mock(AppointmentsProcessStarterPort.class);

	private final AppointmentReservationService useCase = new AppointmentReservationServiceImpl(this.processManager);

	@Test
	void execute_shouldCallStartAppointmentProcessPort() {

		when(this.processManager.startAppointmentProcess(any()))
				.thenReturn(Optional.of(new ProcessResult("process-id", "ACTIVE")));

		final AppointmentReservationResponse response = this.useCase.execute(getRequest());

		verify(this.processManager).startAppointmentProcess(mockedSymptoms());

		assertThat(response).isNotNull();
		assertThat(response).isEqualTo(new AppointmentReservationResponse("process-id", "ACTIVE"));

	}

	@Test
	void execute_shouldThrowWhenRequestIsNull() {
		assertThatThrownBy(() -> this.useCase.execute(null)).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("request must not be null");
	}

	@Test
	void execute_shouldThrowWhenProcessResultIsEmpty() {
		when(this.processManager.startAppointmentProcess(any())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> this.useCase.execute(getRequest())).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Appointment process could not be started");
	}

	private static AppointmentReservationRequest getRequest() {
		return new AppointmentReservationRequest(PATIENT_ID, 37.5, 3, ChestPainType.SEVERE, FatigueType.YES, true,
				false);
	}

	private static PatientSymptoms mockedSymptoms() {
		return PatientSymptoms.builder().patientId(PATIENT_ID).temperature(37.5).coughFrequencyPerHour(3)
				.chestPainType(ChestPainType.SEVERE).fatigueType(FatigueType.YES).hasWeightLoss(true)
				.hasNeckStiffness(false).build();
	}
}
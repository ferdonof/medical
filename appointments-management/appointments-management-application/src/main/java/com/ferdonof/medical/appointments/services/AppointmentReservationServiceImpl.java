package com.ferdonof.medical.appointments.services;

import java.util.Objects;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessStarterPort;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;
import com.ferdonof.medical.commons.entities.ProcessResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppointmentReservationServiceImpl implements AppointmentReservationService {
	private final AppointmentsProcessStarterPort processManager;

	@Override
	public AppointmentReservationResponse execute(AppointmentReservationRequest request) {
		Objects.requireNonNull(request, "request must not be null");
		final ProcessResult processResult = this.processManager.startAppointmentProcess(this.toSymptoms(request))
				.orElseThrow(() -> new IllegalStateException("Appointment process could not be started"));
		return new AppointmentReservationResponse(processResult.processId(), processResult.status());
	}

	private PatientSymptoms toSymptoms(AppointmentReservationRequest request) {
		return PatientSymptoms.builder().patientId(request.patientId()).temperature(request.temperature())
				.coughFrequencyPerHour(request.coughFrequencyPerHour()).chestPainType(request.chestPainType())
				.fatigueType(request.fatigueType()).hasWeightLoss(request.hasWeightLoss())
				.hasNeckStiffness(request.hasNeckStiffness()).build();
	}

}

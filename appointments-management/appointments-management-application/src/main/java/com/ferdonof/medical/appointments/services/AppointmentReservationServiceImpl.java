package com.ferdonof.medical.appointments.services;

import lombok.RequiredArgsConstructor;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessStarterPort;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;
import com.ferdonof.medical.commons.entities.ProcessResult;

@RequiredArgsConstructor
public class AppointmentReservationServiceImpl implements AppointmentReservationService {
	private final AppointmentsProcessStarterPort processManager;

	@Override
	public AppointmentReservationResponse execute(AppointmentReservationRequest request) {
		final ProcessResult processResult = this.processManager.startAppointmentProcess(this.toSymptoms(request));
		return new AppointmentReservationResponse(processResult.processId(), processResult.status());
	}

	private PatientSymptoms toSymptoms(AppointmentReservationRequest request) {
		return PatientSymptoms.builder()
				.patientId(request.patientId())
				.temperature(request.temperature())
				.coughFrequencyPerHour(request.coughFrequencyPerHour())
				.chestPainType(request.chestPainType())
				.fatigueType(request.fatigueType())
				.hasWeightLoss(request.hasWeightLoss())
				.hasNeckStiffness(request.hasNeckStiffness())
				.build();
	}

}

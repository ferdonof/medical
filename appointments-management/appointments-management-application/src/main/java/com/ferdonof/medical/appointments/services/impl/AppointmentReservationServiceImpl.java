package com.ferdonof.medical.appointments.services.impl;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessManager;
import com.ferdonof.medical.appointments.services.AppointmentReservationService;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class AppointmentReservationServiceImpl implements AppointmentReservationService {
	private final AppointmentsProcessManager processManager;

	@Override
	public AppointmentReservationResponse execute(AppointmentReservationRequest request) {
		this.processManager.startAppointmentProcess(this.toSymptoms(request));
		return new AppointmentReservationResponse(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
				LocalDateTime.now());
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

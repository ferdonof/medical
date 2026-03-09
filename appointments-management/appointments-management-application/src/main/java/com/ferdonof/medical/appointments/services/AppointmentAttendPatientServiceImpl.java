package com.ferdonof.medical.appointments.services;

import java.util.Objects;

import com.ferdonof.medical.appointments.ports.AppointmentAttendPatientPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AppointmentAttendPatientServiceImpl implements AppointmentAttendPatientService {

	private final AppointmentAttendPatientPort attendPatientPort;

	@Override
	public void execute(final String processId) {
		Objects.requireNonNull(processId, "processId must not be null");
		log.info("Executing AppointmentPatientSeenService with processId: {}", processId);
		this.attendPatientPort.attendPatient(processId);
	}
}

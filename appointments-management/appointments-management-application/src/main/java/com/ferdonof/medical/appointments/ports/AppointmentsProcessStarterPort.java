package com.ferdonof.medical.appointments.ports;

import java.util.Optional;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.commons.entities.ProcessResult;
import com.ferdonof.medical.commons.ports.ProcessManager;

public interface AppointmentsProcessStarterPort extends ProcessManager {
	Optional<ProcessResult> startAppointmentProcess(PatientSymptoms symptoms);

}

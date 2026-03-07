package com.ferdonof.medical.appointments.ports;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.commons.ports.ProcessManager;

public interface AppointmentsProcessStarterPort extends ProcessManager {
	void startAppointmentProcess(PatientSymptoms symptoms);

}

package com.ferdonof.medical.appointments.ports;

import com.ferdonof.medical.commons.ports.ProcessManager;

public interface AppointmentAttendPatientPort extends ProcessManager {
	void attendPatient(String processId);

}

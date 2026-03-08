package com.ferdonof.medical.appointments.ports;

import com.ferdonof.medical.commons.ports.ProcessManager;

public interface AppointmentConfirmationHandlerPort extends ProcessManager{

  void confirmAppointment(String processId);

}

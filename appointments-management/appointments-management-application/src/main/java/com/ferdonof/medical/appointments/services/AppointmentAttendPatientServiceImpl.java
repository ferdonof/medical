package com.ferdonof.medical.appointments.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ferdonof.medical.appointments.ports.AppointmentAttendPatientPort;

@Slf4j
@RequiredArgsConstructor
public class AppointmentAttendPatientServiceImpl implements AppointmentAttendPatientService
{

  private final AppointmentAttendPatientPort attendPatientPort;

  @Override public void execute(final String processId) {
    log.info("Executing AppointmentPatientSeenService with processId: {}", processId);
    this.attendPatientPort.attendPatient(processId);
  }
}

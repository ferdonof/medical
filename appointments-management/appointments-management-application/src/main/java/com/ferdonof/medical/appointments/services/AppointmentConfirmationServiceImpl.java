package com.ferdonof.medical.appointments.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ferdonof.medical.appointments.ports.AppointmentConfirmationHandlerPort;

@Slf4j
@RequiredArgsConstructor
public class AppointmentConfirmationServiceImpl implements AppointmentConfirmationService{

  private final AppointmentConfirmationHandlerPort appointmentConfirmationHandlerPort;

  @Override
  public void execute(final String processId) {
    this.appointmentConfirmationHandlerPort.confirmAppointment(processId);
  }
}

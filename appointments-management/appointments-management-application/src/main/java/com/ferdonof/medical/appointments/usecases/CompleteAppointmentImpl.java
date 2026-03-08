package com.ferdonof.medical.appointments.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;

@Slf4j
@RequiredArgsConstructor
public class CompleteAppointmentImpl implements CompleteAppointment{

  private final AppointmentSlotRepositoryPort appointmentSlotRepositoryPort;

  @Override
  public void execute(final UUID slotId) {
    log.info("Executing CompleteAppointment with slotId: {}", slotId);
    this.appointmentSlotRepositoryPort.completeAppointment(slotId);
  }
}

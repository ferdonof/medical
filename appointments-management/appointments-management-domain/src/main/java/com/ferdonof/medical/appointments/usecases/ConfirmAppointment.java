package com.ferdonof.medical.appointments.usecases;

import java.util.UUID;

public interface ConfirmAppointment {
  void execute(UUID slotId);
}

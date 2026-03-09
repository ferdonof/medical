package com.ferdonof.medical.appointments.usecases;

import java.util.UUID;

public interface CompleteAppointment {
  void execute(UUID slotId);
}

package com.ferdonof.medical.appointments.usecases;

import java.util.UUID;

public interface CancelAppointment
{
  void execute(UUID slotId);
}

package com.ferdonof.medical.appointments.entities;

import com.ferdonof.medical.appointments.enums.SlotStatus;
import com.ferdonof.medical.commons.entities.Doctor;

import java.util.UUID;

public record AppointmentSlot(UUID id, Doctor doctor, String dateTime, SlotStatus status) {
}

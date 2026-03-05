package com.ferdonof.medical.appointments.services.entities;

import com.ferdonof.medical.appointments.enums.ChestPainType;
import com.ferdonof.medical.appointments.enums.FatigueType;

import java.util.UUID;

public record AppointmentReservationRequest(UUID patientId, double temperature, int coughFrequencyPerHour,
		ChestPainType chestPainType, FatigueType fatigueType, boolean hasWeightLoss, boolean hasNeckStiffness) {
}

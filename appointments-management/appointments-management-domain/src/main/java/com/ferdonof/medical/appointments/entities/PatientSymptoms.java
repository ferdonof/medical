package com.ferdonof.medical.appointments.entities;

import com.ferdonof.medical.appointments.enums.ChestPainType;
import com.ferdonof.medical.appointments.enums.FatigueType;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record PatientSymptoms(UUID patientId, double temperature, int coughFrequencyPerHour,
		ChestPainType chestPainType, FatigueType fatigueType, boolean hasWeightLoss, boolean hasNeckStiffness) {
}

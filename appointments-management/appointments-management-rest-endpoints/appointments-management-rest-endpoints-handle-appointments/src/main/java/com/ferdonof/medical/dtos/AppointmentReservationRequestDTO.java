package com.ferdonof.medical.dtos;

import com.ferdonof.medical.appointments.enums.ChestPainType;
import com.ferdonof.medical.appointments.enums.FatigueType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AppointmentReservationRequestDTO(@NotNull(message = "patientId cannot be null") UUID patientId,
		@NotNull(message = "temperature cannot be null") @Min(value = 35, message = "temperature must be >= 35") Double temperature,
		@NotNull(message = "coughFrequencyPerHour cannot be null") Integer coughFrequencyPerHour,
		@NotNull(message = "chestPainType cannot be null") ChestPainType chestPainType,
		@NotNull(message = "fatigueType cannot be null") FatigueType fatigueType,
		@NotNull(message = "hasWeightLoss cannot be null") Boolean hasWeightLoss,
		@NotNull(message = "hasNeckStiffness cannot be null") Boolean hasNeckStiffness) {
}

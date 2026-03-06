package com.ferdonof.medical.adapters;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.commons.enums.SpecialistType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppointmentSlotRepositoryAdapter {

	public Optional<AppointmentSlot> findFirstSlotAvailable(SpecialistType specialistType) {
		return Optional.empty();
	}

}

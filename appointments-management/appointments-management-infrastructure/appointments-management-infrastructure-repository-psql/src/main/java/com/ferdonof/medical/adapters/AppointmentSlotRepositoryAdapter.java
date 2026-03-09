package com.ferdonof.medical.adapters;

import lombok.RequiredArgsConstructor;
import static com.ferdonof.medical.appointments.enums.SlotStatus.BOOKED;
import static com.ferdonof.medical.appointments.enums.SlotStatus.CANCELLED;
import static com.ferdonof.medical.appointments.enums.SlotStatus.COMPLETED;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.commons.enums.SpecialistType;
import com.ferdonof.medical.repositories.AppointmentSlotRepository;

@Component
@RequiredArgsConstructor
public class AppointmentSlotRepositoryAdapter implements AppointmentSlotRepositoryPort {

	private final AppointmentSlotRepository appointmentSlotRepository;

	@Override
	public Optional<AppointmentSlot> reserveFirstAvailableSlot(java.util.UUID patientId, SpecialistType specialistType,
			java.time.LocalDateTime appointmentAfter) {
		return this.appointmentSlotRepository.reserveFirstAvailableSlot(patientId, specialistType, appointmentAfter);
	}

	@Override
	public void confirmAppointment(java.util.UUID slotId) {
		this.appointmentSlotRepository.updateAppointmentStatus(slotId, BOOKED);
	}

	@Override
	public void cancelAppointment(java.util.UUID slotId) {
		this.appointmentSlotRepository.updateAppointmentStatus(slotId, CANCELLED);
	}

	@Override
	public void completeAppointment(java.util.UUID slotId) {
		this.appointmentSlotRepository.updateAppointmentStatus(slotId, COMPLETED);
	}

}

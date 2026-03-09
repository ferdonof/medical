package com.ferdonof.medical.appointments.usecases;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.enums.SlotStatus;
import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.commons.entities.Doctor;
import com.ferdonof.medical.commons.enums.SpecialistType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReserveFirstAvailableSlotTest {

	private static final UUID PATIENT_ID = UUID.randomUUID();

	private final AppointmentSlotRepositoryPort repositoryPort = mock(AppointmentSlotRepositoryPort.class);

	private final ReserveFirstAvailableSlot useCase = new ReserveFirstAvailableSlotImpl(this.repositoryPort);

	@Test
	void execute_shouldReturnSlotWhenAvailable() {
		final AppointmentSlot expectedSlot = buildSlot();
		when(this.repositoryPort.reserveFirstAvailableSlot(eq(PATIENT_ID), eq(SpecialistType.CARDIOLOGIST), any()))
				.thenReturn(Optional.of(expectedSlot));

		final Optional<AppointmentSlot> result = this.useCase.execute(PATIENT_ID, SpecialistType.CARDIOLOGIST);

		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(expectedSlot);
		verify(this.repositoryPort).reserveFirstAvailableSlot(eq(PATIENT_ID), eq(SpecialistType.CARDIOLOGIST), any());
	}

	@Test
	void execute_shouldReturnEmptyWhenNoSlotAvailable() {
		when(this.repositoryPort.reserveFirstAvailableSlot(eq(PATIENT_ID), eq(SpecialistType.CARDIOLOGIST), any()))
				.thenReturn(Optional.empty());

		final Optional<AppointmentSlot> result = this.useCase.execute(PATIENT_ID, SpecialistType.CARDIOLOGIST);

		assertThat(result).isNotNull();
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	void execute_shouldPassAppointmentAfterAtLeast90MinutesFromNow() {
		when(this.repositoryPort.reserveFirstAvailableSlot(any(), any(), any())).thenReturn(Optional.empty());

		final LocalDateTime before = LocalDateTime.now().plusMinutes(90);
		this.useCase.execute(PATIENT_ID, SpecialistType.GENERAL_PRACTITIONER);
		final LocalDateTime after = LocalDateTime.now().plusMinutes(91);

		verify(this.repositoryPort).reserveFirstAvailableSlot(eq(PATIENT_ID), eq(SpecialistType.GENERAL_PRACTITIONER),
				argThat(dt -> !dt.isBefore(before) && !dt.isAfter(after)));
	}

	private static AppointmentSlot buildSlot() {
		final Doctor doctor = Doctor.builder().id(UUID.randomUUID()).name("Dr. Nick Riviera")
				.specialty(SpecialistType.CARDIOLOGIST).build();
		return AppointmentSlot.builder().id(UUID.randomUUID()).doctor(doctor)
				.appointmentAt(LocalDateTime.now().plusDays(1)).reservedAt(LocalDateTime.now())
				.status(SlotStatus.RESERVED).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
	}

	private static <T> T argThat(org.mockito.ArgumentMatcher<T> matcher) {
		return org.mockito.ArgumentMatchers.argThat(matcher);
	}
}

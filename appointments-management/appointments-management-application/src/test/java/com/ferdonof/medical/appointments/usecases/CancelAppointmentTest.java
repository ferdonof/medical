package com.ferdonof.medical.appointments.usecases;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelAppointmentTest {

	private final AppointmentSlotRepositoryPort repositoryPort = mock(AppointmentSlotRepositoryPort.class);

	private final CancelAppointment useCase = new CancelAppointmentImpl(this.repositoryPort);

	@Test
	void execute_shouldCallCancelAppointmentOnRepositoryPort() {
		final UUID slotId = UUID.randomUUID();

		doNothing().when(this.repositoryPort).cancelAppointment(any());

		this.useCase.execute(slotId);

		verify(this.repositoryPort).cancelAppointment(slotId);
	}

	@Test
	void execute_shouldThrowWhenSlotIdIsNull() {
		assertThatThrownBy(() -> this.useCase.execute(null)).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("slotId must not be null");
	}

}
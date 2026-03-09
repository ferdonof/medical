package com.ferdonof.medical;

import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.appointments.usecases.CancelAppointment;
import com.ferdonof.medical.appointments.usecases.CompleteAppointment;
import com.ferdonof.medical.appointments.usecases.ConfirmAppointment;
import com.ferdonof.medical.appointments.usecases.ReserveFirstAvailableSlot;
import com.ferdonof.medical.delegates.CancelAppointmentDelegate;
import com.ferdonof.medical.delegates.CompleteAppointmentDelegate;
import com.ferdonof.medical.delegates.ConfirmAppointmentDelegate;
import com.ferdonof.medical.delegates.ReserveFirstAvailableSlotDelegate;
import com.ferdonof.medical.delegates.SendConfirmationEmailDelegate;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAppointmentProcessApplication {

	@Bean
	public AppointmentSlotRepositoryPort appointmentSlotRepositoryPort() {
		return Mockito.mock(AppointmentSlotRepositoryPort.class);
	}

	@Bean
	public ReserveFirstAvailableSlot reserveFirstAvailableSlot() {
		return Mockito.mock(ReserveFirstAvailableSlot.class);
	}

	@Bean
	public ConfirmAppointment confirmAppointment() {
		return Mockito.mock(ConfirmAppointment.class);
	}

	@Bean
	public CancelAppointment cancelAppointment() {
		return Mockito.mock(CancelAppointment.class);
	}

	@Bean
	public CompleteAppointment completeAppointment() {
		return Mockito.mock(CompleteAppointment.class);
	}

	@Bean
	public ReserveFirstAvailableSlotDelegate reserveFirstAvailableSlotDelegate(
			ReserveFirstAvailableSlot reserveFirstAvailableSlot) {
		return new ReserveFirstAvailableSlotDelegate(reserveFirstAvailableSlot);
	}

	@Bean
	public ConfirmAppointmentDelegate confirmAppointmentDelegate(ConfirmAppointment confirmAppointment) {
		return new ConfirmAppointmentDelegate(confirmAppointment);
	}

	@Bean
	public CancelAppointmentDelegate cancelAppointmentDelegate(CancelAppointment cancelAppointment) {
		return new CancelAppointmentDelegate(cancelAppointment);
	}

	@Bean
	public CompleteAppointmentDelegate completeAppointmentDelegate(CompleteAppointment completeAppointment) {
		return new CompleteAppointmentDelegate(completeAppointment);
	}

	@Bean
	public SendConfirmationEmailDelegate sendConfirmationEmailDelegate() {
		return new SendConfirmationEmailDelegate();
	}
}

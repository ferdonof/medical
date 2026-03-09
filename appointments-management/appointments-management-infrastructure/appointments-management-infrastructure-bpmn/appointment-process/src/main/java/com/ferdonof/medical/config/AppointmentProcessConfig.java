package com.ferdonof.medical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.appointments.usecases.CancelAppointment;
import com.ferdonof.medical.appointments.usecases.CancelAppointmentImpl;
import com.ferdonof.medical.appointments.usecases.CompleteAppointment;
import com.ferdonof.medical.appointments.usecases.CompleteAppointmentImpl;
import com.ferdonof.medical.appointments.usecases.ConfirmAppointment;
import com.ferdonof.medical.appointments.usecases.ConfirmAppointmentImpl;
import com.ferdonof.medical.appointments.usecases.ReserveFirstAvailableSlot;
import com.ferdonof.medical.appointments.usecases.ReserveFirstAvailableSlotImpl;

@Configuration
public class AppointmentProcessConfig {

  @Bean
  public ReserveFirstAvailableSlot reserveFirstAvailableSlot(AppointmentSlotRepositoryPort appointmentSlotRepositoryPort) {
    return new ReserveFirstAvailableSlotImpl(appointmentSlotRepositoryPort);
  }

  @Bean
  public ConfirmAppointment confirmAppointment(AppointmentSlotRepositoryPort appointmentSlotRepositoryPort) {
    return new ConfirmAppointmentImpl(appointmentSlotRepositoryPort);
  }

  @Bean
  public CancelAppointment cancelAppointment(AppointmentSlotRepositoryPort appointmentSlotRepositoryPort) {
    return new CancelAppointmentImpl(appointmentSlotRepositoryPort);
  }

  @Bean
  public CompleteAppointment completeAppointment(AppointmentSlotRepositoryPort appointmentSlotRepositoryPort) {
    return new CompleteAppointmentImpl(appointmentSlotRepositoryPort);
  }
}

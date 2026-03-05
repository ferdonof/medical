package com.ferdonof.medical.config;

import com.ferdonof.medical.appointments.ports.AppointmentsProcessManager;
import com.ferdonof.medical.appointments.services.AppointmentReservationService;
import com.ferdonof.medical.appointments.services.impl.AppointmentReservationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentsControllerConfig {

  @Bean
  public AppointmentReservationService appointmentReservationService(AppointmentsProcessManager processManager) {
    return new AppointmentReservationServiceImpl(processManager);
  }

}

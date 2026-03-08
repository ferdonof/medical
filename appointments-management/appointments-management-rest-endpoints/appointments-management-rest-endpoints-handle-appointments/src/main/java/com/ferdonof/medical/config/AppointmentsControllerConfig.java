package com.ferdonof.medical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ferdonof.medical.appointments.ports.AppointmentAttendPatientPort;
import com.ferdonof.medical.appointments.ports.AppointmentConfirmationHandlerPort;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessStarterPort;
import com.ferdonof.medical.appointments.services.AppointmentAttendPatientService;
import com.ferdonof.medical.appointments.services.AppointmentAttendPatientServiceImpl;
import com.ferdonof.medical.appointments.services.AppointmentConfirmationService;
import com.ferdonof.medical.appointments.services.AppointmentConfirmationServiceImpl;
import com.ferdonof.medical.appointments.services.AppointmentReservationService;
import com.ferdonof.medical.appointments.services.AppointmentReservationServiceImpl;

@Configuration
public class AppointmentsControllerConfig {

  @Bean
	public AppointmentReservationService appointmentReservationService(AppointmentsProcessStarterPort processManager) {
    return new AppointmentReservationServiceImpl(processManager);
  }

	@Bean
	public AppointmentConfirmationService appointmentConfirmationService(
			AppointmentConfirmationHandlerPort appointmentConfirmationHandlerPort) {
		return new AppointmentConfirmationServiceImpl(appointmentConfirmationHandlerPort);
	}

	@Bean
	public AppointmentAttendPatientService patientSeenService(
			AppointmentAttendPatientPort appointmentMarkPatientSeenPort) {
		return new AppointmentAttendPatientServiceImpl(appointmentMarkPatientSeenPort);
	}
}

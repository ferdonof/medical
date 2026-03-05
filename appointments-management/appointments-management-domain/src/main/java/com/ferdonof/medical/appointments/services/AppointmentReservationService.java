package com.ferdonof.medical.appointments.services;

import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;

public interface AppointmentReservationService {
	AppointmentReservationResponse execute(AppointmentReservationRequest request);
}

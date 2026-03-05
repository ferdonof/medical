package com.ferdonof.medical.controllers;

import com.ferdonof.medical.appointments.services.AppointmentReservationService;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.dtos.AppointmentReservationRequestDTO;
import com.ferdonof.medical.dtos.AppointmentReservationResponseDTO;
import com.ferdonof.medical.mappers.AppointmentReservationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

	private final AppointmentReservationMapper reservationMapper;
	private final AppointmentReservationService reservationService;

	@GetMapping
	public String get() {
		return "Get Appointments is working!";
	}

	@PostMapping
	public AppointmentReservationResponseDTO createAppointment(
			@Valid @RequestBody AppointmentReservationRequestDTO request) {
		final AppointmentReservationRequest domainRequest = this.reservationMapper.map(request);
		return this.reservationMapper.map(this.reservationService.execute(domainRequest));
	}

}

package com.ferdonof.medical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ferdonof.medical.appointments.services.AppointmentAttendPatientService;
import com.ferdonof.medical.appointments.services.AppointmentConfirmationService;
import com.ferdonof.medical.appointments.services.AppointmentReservationService;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.dtos.AppointmentInfoDTO;
import com.ferdonof.medical.dtos.AppointmentReservationRequestDTO;
import com.ferdonof.medical.dtos.AppointmentReservationResponseDTO;
import com.ferdonof.medical.mappers.AppointmentReservationMapper;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

	private final AppointmentReservationMapper reservationMapper;
	private final AppointmentReservationService reservationService;
	private final AppointmentConfirmationService confirmationService;
	private final AppointmentAttendPatientService attendPatientService;

	@GetMapping
	public String get() {
		return "Get Appointments is working!";
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AppointmentReservationResponseDTO createAppointment(
			@Valid @RequestBody AppointmentReservationRequestDTO request) {
		final AppointmentReservationRequest domainRequest = this.reservationMapper.map(request);
		return this.reservationMapper.map(this.reservationService.execute(domainRequest));
	}

	@PostMapping("/confirm")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void confirm(@Valid @RequestBody AppointmentInfoDTO request) {
		this.confirmationService.execute(request.id());
	}

	@PostMapping("/attend")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void markAsSeen(@Valid @RequestBody AppointmentInfoDTO request) {
		this.attendPatientService.execute(request.id());
	}
}

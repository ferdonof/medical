package com.ferdonof.medical.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

	@GetMapping
	public String getAppointments() {
		return "Get Appointments";
	}

}

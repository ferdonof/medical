package com.ferdonof.medical.controllers;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

	private final RuntimeService runtimeService;

	@GetMapping
	public String getAppointments() {
		return "Get Appointments";
	}

	@GetMapping("/processes/active")
	public List<ProcessInstance> getActiveProcesses() {

		return this.runtimeService.createProcessInstanceQuery().active().list();
	}

}

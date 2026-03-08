package com.ferdonof.medical.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import com.ferdonof.medical.appointments.ports.AppointmentConfirmationHandlerPort;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentConfirmationHandlerAdapter implements AppointmentConfirmationHandlerPort {

	public static final String MESSAGE_NAME = "ConfirmAppointmentMessage";

	private final RuntimeService runtimeService;

	@Override
	public void confirmAppointment(final String processId) {
		this.runtimeService.createMessageCorrelation(MESSAGE_NAME).processInstanceId(processId).correlate();
	}
}

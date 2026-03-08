package com.ferdonof.medical.delegates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.ferdonof.medical.appointments.usecases.ConfirmAppointment;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConfirmAppointmentDelegate implements JavaDelegate {

	private final ConfirmAppointment confirmAppointment;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		log.info("Executing ConfirmAppointmentDelegate for process instance id: {}", delegateExecution.getProcessInstanceId());

		final UUID slotId = (UUID) delegateExecution.getVariable("slotId");
		this.confirmAppointment.execute(slotId);

	}
}

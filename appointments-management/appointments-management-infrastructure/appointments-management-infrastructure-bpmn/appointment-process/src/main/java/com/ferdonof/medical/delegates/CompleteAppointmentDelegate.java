package com.ferdonof.medical.delegates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.ferdonof.medical.appointments.usecases.CompleteAppointment;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteAppointmentDelegate implements JavaDelegate {

	private final CompleteAppointment completeAppointment;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		log.info("Executing CompleteAppointmentDelegate for process instance id: {}", delegateExecution.getProcessInstanceId());

		final UUID slotId = (UUID) delegateExecution.getVariable("slotId");
		this.completeAppointment.execute(slotId);

	}
}

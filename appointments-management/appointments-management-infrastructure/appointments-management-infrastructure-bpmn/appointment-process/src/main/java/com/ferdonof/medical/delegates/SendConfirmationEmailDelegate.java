package com.ferdonof.medical.delegates;

import lombok.extern.slf4j.Slf4j;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendConfirmationEmailDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		log.info("Executing SendConfirmationEmailDelegate for process instance id: {}", delegateExecution.getProcessInstanceId());

		log.info("*************************************************************************************************************");
		log.info("Sending email to patient with the following details:");
		log.info("     Patient Id          :: {}", delegateExecution.getVariable("patientId"));
		log.info("     SlotId              :: {}", delegateExecution.getVariable("slotId"));
		log.info("     Doctor code         :: {}", delegateExecution.getVariable("doctorCode"));
		log.info("     Appointment date    :: {}", delegateExecution.getVariable("appointmentDate"));
		log.info("     Process instance id :: {}", delegateExecution.getProcessInstanceId());
		log.info("*************************************************************************************************************");

	}
}

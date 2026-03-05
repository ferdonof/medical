package com.ferdonof.medical.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MedicalGenericDelegate implements JavaDelegate {
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("Variables in MedicalGenericDelegate");
		System.out.println(delegateExecution.getVariables());
		if (delegateExecution.getVariable("specialistDecision") != null
				&& !((Map) delegateExecution.getVariable("specialistDecision")).isEmpty()) {
			final Map<String, Object> specialistDecision = (Map<String, Object>) delegateExecution
					.getVariable("specialistDecision");

			final String disease = (String) specialistDecision.get("disease");
			final String specialist = (String) specialistDecision.get("specialist");

			System.out.println("Specialist decision:");
			System.out.println("Disease: " + disease);
			System.out.println("Specialist: " + specialist);
		}
		System.out.println("---------------");
	}
}

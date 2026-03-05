package com.ferdonof.medical.adapters;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessManager;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentProcessManagerAdapter implements AppointmentsProcessManager {

	private static final String PATIENT_ID = "patientId";
	private static final String TEMPERATURE = "temperature";
	private static final String COUGH_FREQUENCY = "coughFrequencyPerHour";
	private static final String CHEST_PAIN_TYPE = "chestPainType";
	private static final String FATIGUE_TYPE = "fatigueType";
	private static final String WEIGHT_LOSS = "hasWeightLoss";
	private static final String NECK_STIFFNESS = "hasNeckStiffness";

	private final RuntimeService runtimeService;

	@Override
	public void startAppointmentProcess(PatientSymptoms symptoms) {
		final ProcessInstance appointmentProcess = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.toProcessVariables(symptoms));

		final String processInstanceId = appointmentProcess.getId();
		System.out.println("Started appointment process with ID: " + processInstanceId);
		System.out.println("Process variables: " + this.runtimeService.getVariables(processInstanceId));

	}

	private Map<String, Object> toProcessVariables(PatientSymptoms symptoms) {
		return Map.of(PATIENT_ID, symptoms.patientId(), TEMPERATURE, symptoms.temperature(), COUGH_FREQUENCY,
				symptoms.coughFrequencyPerHour(), CHEST_PAIN_TYPE, symptoms.chestPainType().getDescription(),
				FATIGUE_TYPE, symptoms.fatigueType().getDescription(), WEIGHT_LOSS, symptoms.hasWeightLoss(),
				NECK_STIFFNESS, symptoms.hasNeckStiffness());
	}

}

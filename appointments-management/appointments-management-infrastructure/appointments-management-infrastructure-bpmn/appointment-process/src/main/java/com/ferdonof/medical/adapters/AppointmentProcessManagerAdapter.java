package com.ferdonof.medical.adapters;

import java.util.Map;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

@Slf4j
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

	private final HistoryService historyService;

	@Override
	public void startAppointmentProcess(PatientSymptoms symptoms) {
		final ProcessInstance appointmentProcess = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.toProcessVariables(symptoms));

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(appointmentProcess.getProcessInstanceId()).singleResult();

		final String processInstanceId = historic.getId();

		log.info("Appointment process with ID: {} and status: {}", processInstanceId, historic.getState());

	}

	private Map<String, Object> toProcessVariables(PatientSymptoms symptoms) {
		return Map.of(PATIENT_ID, symptoms.patientId(), TEMPERATURE, symptoms.temperature(), COUGH_FREQUENCY,
				symptoms.coughFrequencyPerHour(), CHEST_PAIN_TYPE, symptoms.chestPainType().getDescription(),
				FATIGUE_TYPE, symptoms.fatigueType().getDescription(), WEIGHT_LOSS, symptoms.hasWeightLoss(),
				NECK_STIFFNESS, symptoms.hasNeckStiffness());
	}

}

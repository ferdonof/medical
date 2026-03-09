package com.ferdonof.medical.adapters;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.ferdonof.medical.appointments.entities.PatientSymptoms;
import com.ferdonof.medical.appointments.ports.AppointmentsProcessStarterPort;
import com.ferdonof.medical.commons.entities.ProcessResult;
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
public class AppointmentProcessStarterAdapter implements AppointmentsProcessStarterPort {

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
	public Optional<ProcessResult> startAppointmentProcess(PatientSymptoms symptoms) {
		Objects.requireNonNull(symptoms, "symptoms must not be null");

		final ProcessInstance appointmentProcess = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.toProcessVariables(symptoms));

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(appointmentProcess.getProcessInstanceId()).singleResult();

		if (historic == null) {
			log.warn("No historic process instance found for process ID: {}",
					appointmentProcess.getProcessInstanceId());
			return Optional.empty();
		}

		log.info("Appointment process with ID: {} and status: {}", historic.getId(), historic.getState());

		return Optional.of(new ProcessResult(historic.getId(), historic.getState()));
	}

	private Map<String, Object> toProcessVariables(PatientSymptoms symptoms) {
		return Map.of(PATIENT_ID, symptoms.patientId(), TEMPERATURE, symptoms.temperature(), COUGH_FREQUENCY,
				symptoms.coughFrequencyPerHour(), CHEST_PAIN_TYPE, symptoms.chestPainType().getDescription(),
				FATIGUE_TYPE, symptoms.fatigueType().getDescription(), WEIGHT_LOSS, symptoms.hasWeightLoss(),
				NECK_STIFFNESS, symptoms.hasNeckStiffness());
	}

}

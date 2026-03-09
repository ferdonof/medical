package com.ferdonof.medical.integration;

import com.ferdonof.medical.TestAppointmentProcessApplication;
import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.enums.ChestPainType;
import com.ferdonof.medical.appointments.enums.FatigueType;
import com.ferdonof.medical.appointments.usecases.ReserveFirstAvailableSlot;
import com.ferdonof.medical.commons.entities.Doctor;
import com.ferdonof.medical.commons.enums.SpecialistType;
import com.ferdonof.medical.delegates.CancelAppointmentDelegate;
import com.ferdonof.medical.delegates.CompleteAppointmentDelegate;
import com.ferdonof.medical.delegates.ConfirmAppointmentDelegate;
import com.ferdonof.medical.delegates.ReserveFirstAvailableSlotDelegate;
import com.ferdonof.medical.delegates.SendConfirmationEmailDelegate;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppointmentProcessApplication.class)
@Deployment(resources = {"assets/medical_appointment_process.bpmn", "assets/decide_specialist.dmn"})
public class AppointmentProcessTest {
	public static final String PATIENT_ID = "patientId";
	public static final String TEMPERATURE = "temperature";
	public static final String COUGH_FREQUENCY_PER_HOUR = "coughFrequencyPerHour";
	public static final String CHEST_PAIN_TYPE = "chestPainType";
	public static final String FATIGUE_TYPE = "fatigueType";
	public static final String HAS_WEIGHT_LOSS = "hasWeightLoss";
	public static final String HAS_NECK_STIFFNESS = "hasNeckStiffness";

	@RegisterExtension
	static ProcessEngineExtension processEngineExtension = ProcessEngineExtension.builder()
			.configurationResource("camunda.cfg.xml").build();

	ProcessEngine processEngine;
	RuntimeService runtimeService;
	TaskService taskService;
	HistoryService historyService;

	@Autowired
	ReserveFirstAvailableSlotDelegate reserveFirstAvailableSlotDelegate;

	@Autowired
	ConfirmAppointmentDelegate confirmAppointmentDelegate;

	@Autowired
	CancelAppointmentDelegate cancelAppointmentDelegate;

	@Autowired
	CompleteAppointmentDelegate completeAppointmentDelegate;

	@Autowired
	SendConfirmationEmailDelegate sendConfirmationEmailDelegate;

	@Autowired
	ReserveFirstAvailableSlot reserveFirstAvailableSlot;

	@BeforeEach
	void registerDelegates() {
		this.processEngine = processEngineExtension.getProcessEngine();
		this.runtimeService = this.processEngine.getRuntimeService();
		this.taskService = this.processEngine.getTaskService();
		this.historyService = this.processEngine.getHistoryService();

		Mocks.register("reserveFirstAvailableSlotDelegate", this.reserveFirstAvailableSlotDelegate);
		Mocks.register("confirmAppointmentDelegate", this.confirmAppointmentDelegate);
		Mocks.register("cancelAppointmentDelegate", this.cancelAppointmentDelegate);
		Mocks.register("completeAppointmentDelegate", this.completeAppointmentDelegate);
		Mocks.register("sendConfirmationEmailDelegate", this.sendConfirmationEmailDelegate);
		init(this.processEngine);
	}

	@Test
	void shouldCompleteProcess_whenSlotAvailableAndConfirmedWithReminder() {
		final AppointmentSlot slot = this.buildSlot(LocalDateTime.now().plusDays(10));
		when(this.reserveFirstAvailableSlot.execute(any(), any())).thenReturn(Optional.of(slot));

		final var processInstance = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.influenzaVariables());

		assertThat(processInstance).isNotNull();
		assertThat(processInstanceQuery().processInstanceId(processInstance.getId()).active().count()).isEqualTo(1);

		this.runtimeService.correlateMessage("ConfirmAppointmentMessage");

		assertThat(processInstanceQuery().processInstanceId(processInstance.getId()).active().count()).isEqualTo(1);

		assertThat(processInstance).isNotNull();
		execute(job(processInstance));

		assertThat(processInstance).isWaitingAt("Activity_1r5e2zk");
		complete(task(processInstance));

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		assertThat(historic.getState()).isEqualTo(HistoricProcessInstance.STATE_COMPLETED);
	}

	@Test
	void shouldCompleteProcess_whenSlotAvailableAndConfirmedWithoutReminder() {
		final AppointmentSlot slot = this.buildSlot(LocalDateTime.now().plusDays(1));
		when(this.reserveFirstAvailableSlot.execute(any(), any())).thenReturn(Optional.of(slot));

		final var processInstance = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.influenzaVariables());

		this.runtimeService.correlateMessage("ConfirmAppointmentMessage");

		assertThat(processInstance).isWaitingAt("Activity_1r5e2zk");
		complete(task(processInstance));

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		assertThat(historic.getState()).isEqualTo(HistoricProcessInstance.STATE_COMPLETED);
	}

	@Test
	void shouldEndCancelled_whenNoSlotAvailable() {
		when(this.reserveFirstAvailableSlot.execute(any(), any())).thenReturn(Optional.empty());

		final var processInstance = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.influenzaVariables());

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		assertThat(historic).isNotNull();
		assertThat(historic.getState()).isEqualTo(HistoricProcessInstance.STATE_COMPLETED);

		assertThat(processInstanceQuery().processInstanceId(processInstance.getId()).active().count()).isEqualTo(0);
	}

	@Test
	void shouldEndCancelled_whenConfirmationTimerExpires() {
		final AppointmentSlot slot = this.buildSlot(LocalDateTime.now().plusDays(10));
		when(this.reserveFirstAvailableSlot.execute(any(), any())).thenReturn(Optional.of(slot));

		final var processInstance = this.runtimeService.startProcessInstanceByKey("appointment_process",
				this.influenzaVariables());

		assertThat(processInstanceQuery().processInstanceId(processInstance.getId()).active().count()).isEqualTo(1);

		assertThat(processInstance).isNotNull();
		execute(job(processInstance));

		final HistoricProcessInstance historic = this.historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		assertThat(historic).isNotNull();
		assertThat(historic.getState()).isEqualTo(HistoricProcessInstance.STATE_COMPLETED);
		assertThat(processInstanceQuery().processInstanceId(processInstance.getId()).active().count()).isEqualTo(0);
	}

	private Map<String, Object> influenzaVariables() {
		return Map.of(PATIENT_ID, UUID.randomUUID(), TEMPERATURE, 38.0, COUGH_FREQUENCY_PER_HOUR, 15, CHEST_PAIN_TYPE,
				ChestPainType.NO.getDescription(), FATIGUE_TYPE, FatigueType.YES.getDescription(), HAS_WEIGHT_LOSS,
				false, HAS_NECK_STIFFNESS, false);
	}

	private AppointmentSlot buildSlot(LocalDateTime appointmentAt) {
		final Doctor doctor = Doctor.builder().id(UUID.randomUUID()).name("Dr. Nick Riviera")
				.specialty(SpecialistType.GENERAL_PRACTITIONER).build();
		return AppointmentSlot.builder().id(UUID.randomUUID()).doctor(doctor).appointmentAt(appointmentAt).build();
	}
}

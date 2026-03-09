package com.ferdonof.medical.delegates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.usecases.ReserveFirstAvailableSlot;
import com.ferdonof.medical.commons.enums.SpecialistType;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveFirstAvailableSlotDelegate implements JavaDelegate {

	private final ReserveFirstAvailableSlot reserveFirstAvailableSlot;

	@Override
	@SuppressWarnings("unchecked")
	public void execute(DelegateExecution delegateExecution) throws Exception {
		log.info("Executing ReserveFirstAvailableSlotDelegate for process instance id: {}", delegateExecution.getProcessInstanceId());

		final Object variable = delegateExecution.getVariable("medResult");
		if (!(variable instanceof Map)) {
			throw new BpmnError("MISSING_MED_RESULT", "The variable 'medResult' is missing or not a Map");
		}
		final Map<String, Object> medResult = (Map<String, Object>) variable;
		final UUID patientId = (UUID) delegateExecution.getVariable("patientId");
		final SpecialistType specialty = SpecialistType.fromDescription((String) medResult.get("specialist"));

		final Optional<AppointmentSlot> apSlotOpt = this.reserveFirstAvailableSlot.execute(patientId, specialty);
		if (apSlotOpt.isEmpty()) {
			throw new BpmnError("NO_AVAILABLE_SLOT", "No available appointment slot found for the given specialty");
		}

		delegateExecution.setVariable("slotId", apSlotOpt.get().id());
		final LocalDateTime appointmentAt = apSlotOpt
				.get()
				.appointmentAt();

		delegateExecution.setVariable("appointmentDate", appointmentAt);
		delegateExecution.setVariable("doctorCode", apSlotOpt.get().doctor().id());

		final Date reminderAt = this.toDate(appointmentAt.minusDays(2));
		delegateExecution.setVariable("reminderDate", reminderAt);

		final boolean shouldSendReminder = reminderAt.after(Date.from(Instant.now()));
		delegateExecution.setVariable("shouldSendReminder", shouldSendReminder);
	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}

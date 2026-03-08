package com.ferdonof.medical.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import com.ferdonof.medical.appointments.ports.AppointmentAttendPatientPort;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentAttendPatientAdapter implements AppointmentAttendPatientPort
{

	private final TaskService taskService;
	@Override public void attendPatient(final String processId) {
		final Task task = this.taskService
				.createTaskQuery()
				.processInstanceId(processId)
				.singleResult();

		this.taskService.complete(task.getId());
	}
}

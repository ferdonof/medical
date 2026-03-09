package com.ferdonof.medical.repositories;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.enums.SlotStatus;
import com.ferdonof.medical.commons.enums.SpecialistType;
import com.ferdonof.medical.mappers.AppointmentSlotRowMapper;

@Repository
@RequiredArgsConstructor
public class AppointmentSlotRepository {

	private final AppointmentSlotRowMapper rowMapper;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public Optional<AppointmentSlot> reserveFirstAvailableSlot(UUID patientId, SpecialistType specialistType, LocalDateTime appointmentAfter) {

		final MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("patientId", patientId)
				.addValue("specialty", specialistType.name())
				.addValue("appointmentAfter", appointmentAfter);

		try {
			return Optional.ofNullable(this.jdbcTemplate.queryForObject(
					AppointmentSlotQuery.RESERVE_FIRST_AVAILABLE_SLOT.getSql(), parameters, this.rowMapper));
		} catch (final Exception exception) {
			return Optional.empty();
		}
	}

	public void updateAppointmentStatus(final UUID slotId, SlotStatus status) {
		final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("slotId", slotId)
				.addValue("status", status.name());
		this.jdbcTemplate.update(AppointmentSlotQuery.UPDATE_APPOINTMENT_STATUS.getSql(), parameters);
	}
}

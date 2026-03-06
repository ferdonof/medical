package com.ferdonof.medical.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.ports.AppointmentSlotRepositoryPort;
import com.ferdonof.medical.commons.enums.SpecialistType;
import com.ferdonof.medical.mappers.AppointmentSlotRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppointmentSlotRepository implements AppointmentSlotRepositoryPort {

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
}

package com.ferdonof.medical.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.appointments.enums.SlotStatus;
import com.ferdonof.medical.commons.entities.Doctor;
import com.ferdonof.medical.commons.enums.SpecialistType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AppointmentSlotRowMapper implements RowMapper<AppointmentSlot> {
	@Nullable
	@Override
	public AppointmentSlot mapRow(ResultSet rs, int rowNum) throws SQLException {
		return AppointmentSlot.builder()
						 .id(rs.getObject("id", java.util.UUID.class))
						 .doctor(Doctor.builder()
											 .id(rs.getObject("doctor_id", java.util.UUID.class))
											 .specialty(SpecialistType.valueOf(rs.getString("specialty")))
											 .build())
						 .appointmentAt(rs.getTimestamp("appointment_at") != null ? rs.getTimestamp("appointment_at").toLocalDateTime() : null)
						 .reservedAt(rs.getTimestamp("reserved_at") != null ? rs.getTimestamp("reserved_at").toLocalDateTime() : null)
						 .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
						 .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
						 .status(SlotStatus.valueOf(rs.getString("status")))
						 .build();
	}
}

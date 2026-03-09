package com.ferdonof.medical.repositories;

import com.ferdonof.medical.appointments.entities.AppointmentSlot;
import com.ferdonof.medical.commons.enums.SpecialistType;
import com.ferdonof.medical.config.RepositoryTestApplication;
import com.ferdonof.medical.mappers.AppointmentSlotRowMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.ferdonof.medical.appointments.enums.SlotStatus.AVAILABLE;
import static com.ferdonof.medical.appointments.enums.SlotStatus.CANCELLED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(classes = RepositoryTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppointmentSlotRepositoryTestIT {

	@Container
	static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
			.withUsername("sa").withPassword("password").withDatabaseName("medical-tests-db");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Autowired
	private AppointmentSlotRepository appointmentSlotRepository;

	@Autowired
	private AppointmentSlotRowMapper rowMapper;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	void execute_update() {
		final UUID appointmentSlotId = UUID.fromString("da9f8ec5-de48-44c3-90ff-7c73672c5501");

		final AppointmentSlot slotBefore = this.getSlot(appointmentSlotId);

		this.appointmentSlotRepository.updateAppointmentStatus(appointmentSlotId, CANCELLED);

		final AppointmentSlot slotAfter = this.getSlot(appointmentSlotId);

		assertThat(slotBefore.status()).isEqualTo(AVAILABLE);
		assertThat(slotAfter.status()).isEqualTo(CANCELLED);
		assertThat(slotBefore.updatedAt()).isBefore(slotAfter.updatedAt());
	}

	@Test
	void execute_reserveFirstAvailableSlot() {
		final UUID firstSlotId = UUID.fromString("da9f8ec5-de48-44c3-90ff-7c73672c5501");
		final UUID secondSlotId = UUID.fromString("ce15c391-ebf3-4b65-adbd-287230b1d5ad");

		final UUID firstPatientId = UUID.fromString("f21bbbd6-c69f-449a-a783-a41d5522031a");
		final UUID secondPatientId = UUID.fromString("a3c9f1e2-84d7-4b5a-9f6e-123456789abc");

		final SpecialistType specialistType = SpecialistType.INTERNAL_MEDICINE;
		final LocalDateTime after = LocalDateTime.of(2024, 1, 11, 10, 0, 0);

		final AppointmentSlot firstBefore = this.getSlot(firstSlotId);
		final AppointmentSlot secondBefore = this.getSlot(secondSlotId);

		assertThat(firstBefore.reservedAt()).isNull();

		final Optional<AppointmentSlot> firstReservedSlot = this.appointmentSlotRepository
				.reserveFirstAvailableSlot(firstPatientId, specialistType, after);

		assertThat(firstReservedSlot).isNotEmpty();
		assertThat(firstReservedSlot.get().id()).isEqualTo(firstSlotId);
		assertThat(firstReservedSlot.get().reservedAt()).isNotNull();
		assertThat(firstBefore.updatedAt()).isBefore(firstReservedSlot.get().updatedAt());

		final Optional<AppointmentSlot> secondReservedSlot = this.appointmentSlotRepository
				.reserveFirstAvailableSlot(secondPatientId, specialistType, after);

		assertThat(secondReservedSlot).isNotEmpty();
		assertThat(secondReservedSlot.get().id()).isEqualTo(secondSlotId);
		assertThat(secondReservedSlot.get().reservedAt()).isNotNull();
		assertThat(secondBefore.updatedAt()).isBefore(secondReservedSlot.get().updatedAt());

	}

	private AppointmentSlot getSlot(UUID id) {
		final String sql = "SELECT * FROM appointment_slots WHERE id = :id";
		final MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
		return this.jdbcTemplate.queryForObject(sql, parameters, this.rowMapper);
	}

}
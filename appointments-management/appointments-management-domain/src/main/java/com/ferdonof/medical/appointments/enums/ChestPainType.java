package com.ferdonof.medical.appointments.enums;

import lombok.Getter;

@Getter
public enum ChestPainType {
	YES("Yes"), NO("No"), SOMETIMES("Sometimes"), SEVERE("Severe");

	private final String description;

	ChestPainType(String description) {
		this.description = description;
	}

}

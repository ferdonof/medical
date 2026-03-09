package com.ferdonof.medical.appointments.enums;

import lombok.Getter;

@Getter
public enum FatigueType {
	YES("Yes"), SEVERE("Severe");

	private final String description;

	FatigueType(String description) {
		this.description = description;
	}

}

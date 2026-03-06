package com.ferdonof.medical.commons.enums;

import lombok.Getter;

@Getter
public enum SpecialistType {

	CARDIOLOGIST("Cardiologist"), NEUROLOGIST("Neurologist"), ENDOCRINOLOGIST("Endocrinologist"), PULMONOLOGIST(
			"Pulmonologist"), INTERNAL_MEDICINE("Internal Medicine"), INFECTIOUS_DISEASES(
					"Infectious Disease Specialist"), GENERAL_PRACTITIONER(
							"General Practitioner"), PSYCHIATRIST("Psychiatrist");

	private final String description;

	SpecialistType(String name) {
		this.description = name;
	}

	public static SpecialistType fromDescription(String description) {
		for (final SpecialistType type : SpecialistType.values()) {
			if (type.getDescription().equalsIgnoreCase(description)) {
				return type;
			}
		}
		throw new IllegalArgumentException("No SpecialistType with description: " + description);
	}
}

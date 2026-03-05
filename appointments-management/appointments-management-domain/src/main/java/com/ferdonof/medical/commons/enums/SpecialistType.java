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

}

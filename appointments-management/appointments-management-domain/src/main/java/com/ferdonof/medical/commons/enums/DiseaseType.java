package com.ferdonof.medical.commons.enums;

import lombok.Getter;

@Getter
public enum DiseaseType {
	INFLUENZA("Influenza"), COVID19("Covid-19"), PNEUMONIA("Pneumonia"), TUBERCULOSIS("Tuberculosis"), MONONUCLEOSIS(
			"Mononucleosis"), MENINGITIS("Meningitis"), MYOCARDIAL_INFARCTION("Myocardial Infarction"), PERICARDITIS(
					"Pericarditis"), PANNIC_ATTACK("Panic Attack"), HYPERTHYROIDISM("Hyperthyroidism");

	private final String description;

	DiseaseType(String name) {
		this.description = name;
	}

}

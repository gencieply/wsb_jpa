package com.jpacourse.persistence.enums;

public enum Specialization {

	SURGEON,
	GP,
	DERMATOLOGIST,
	OCULIST;
	public static Specialization fromString(String value) {
		for (Specialization specialization : Specialization.values()) {
			if (specialization.name().equalsIgnoreCase(value)) {
				return specialization;
			}
		}
		throw new IllegalArgumentException("Unknown specialization: " + value);
	}
}

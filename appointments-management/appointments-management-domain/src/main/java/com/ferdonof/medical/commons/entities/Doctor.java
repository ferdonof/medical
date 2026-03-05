package com.ferdonof.medical.commons.entities;

import com.ferdonof.medical.commons.enums.SpecialistType;

import java.util.UUID;

public record Doctor(UUID id, String name, SpecialistType specialty) {
}

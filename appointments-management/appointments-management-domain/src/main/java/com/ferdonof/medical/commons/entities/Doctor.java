package com.ferdonof.medical.commons.entities;

import com.ferdonof.medical.commons.enums.SpecialistType;
import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Doctor(UUID id, String name, SpecialistType specialty) {
}

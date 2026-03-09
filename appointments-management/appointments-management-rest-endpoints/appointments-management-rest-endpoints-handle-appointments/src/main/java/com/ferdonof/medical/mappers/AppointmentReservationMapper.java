package com.ferdonof.medical.mappers;

import com.ferdonof.medical.appointments.services.entities.AppointmentReservationRequest;
import com.ferdonof.medical.appointments.services.entities.AppointmentReservationResponse;
import com.ferdonof.medical.dtos.AppointmentReservationRequestDTO;
import com.ferdonof.medical.dtos.AppointmentReservationResponseDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AppointmentReservationMapper {

	AppointmentReservationRequest map(AppointmentReservationRequestDTO request);

	AppointmentReservationResponseDTO map(AppointmentReservationResponse response);
}

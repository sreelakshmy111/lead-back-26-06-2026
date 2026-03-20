package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.StateDto;
import com.project.Permission.of.lead.entity.*;

import java.time.LocalDateTime;

public class StateMapper {


    public static State mapToState(StateDto stateDto, String createdBy, String updatedBy, String zoneId, String countryId, String regionId, String bussinessUnitId) {
        LocalDateTime now=LocalDateTime.now();
        return  new State(
                stateDto.getId(),
                stateDto.getName(),
                stateDto.getDescription(),
                stateDto.isActive(),
                stateDto.getCreatedAt()!=null? stateDto.getCreatedAt():now,
                createdBy,
                null,
                updatedBy,
                zoneId,
                countryId,
                regionId,
                bussinessUnitId,
                null




        );
    }

    public static StateDto mapToStateDto(State state) {

        return new StateDto(
                state.getId(),
                state.getName(),
                state.getDescription(),
                state.isActive(),
                state.getCreatedAt(),
                state.getCreatedBy(),
                state.getUpdatedAt()!=null? state.getUpdatedAt():null,
                state.getUpdatedBy(),
                state.getZoneId(),
                state.getCountryId(),
                state.getRegionId(),
                state.getBussinessUnitId(),
                state.getSid()
        );

    }



}

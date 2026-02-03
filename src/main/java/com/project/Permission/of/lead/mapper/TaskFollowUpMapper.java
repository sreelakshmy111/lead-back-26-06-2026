package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.TaskFollowUpDto;
import com.project.Permission.of.lead.entity.TaskFollowUp;

import java.time.LocalDateTime;

public class TaskFollowUpMapper {


    public static TaskFollowUp mapToTaskFollowUp(TaskFollowUpDto taskFollowUpDto){
        LocalDateTime now=LocalDateTime.now();
        return new TaskFollowUp(
                taskFollowUpDto.getId(),
                taskFollowUpDto.getBussinessUnitId(),
                taskFollowUpDto.getLeadId(),
                taskFollowUpDto.getNextFollowUp(),
                taskFollowUpDto.getNote(),
                taskFollowUpDto.getCreatedDate(),
                taskFollowUpDto.getCreatedBy(),
                taskFollowUpDto.getUpdatedDate(),
                taskFollowUpDto.getUpdatedBy()
        );
    }

    public static TaskFollowUpDto mapToTaskFollowUpDto(TaskFollowUp taskFollowUp){
        return new TaskFollowUpDto(
                taskFollowUp.getId(),
                taskFollowUp.getBussinessUnitId(),
                taskFollowUp.getLeadId(),
                taskFollowUp.getNextFollowUp(),
                taskFollowUp.getNote(),
                taskFollowUp.getCreatedDate(),
                taskFollowUp.getCreatedBy(),
                taskFollowUp.getUpdatedDate(),
                taskFollowUp.getUpdatedBy()
        );
    }
}

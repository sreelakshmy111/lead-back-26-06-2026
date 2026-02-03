package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.TaskFollowUpDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface TaskFollowUpService {
    List<TaskFollowUpDto> getFollowUps( String buid, String lid, Users loggedInUser);
//    TaskFollowUpDto createFollowUp(String eid, String buid, String lid, TaskFollowUpDto taskFollowUpDtoDto, Users loggedInUser);
}

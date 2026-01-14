package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.UserRoleDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


public interface UserRoleService {

    UserRoleDto assignRoleToUser(@RequestBody UserRoleDto dto);

}

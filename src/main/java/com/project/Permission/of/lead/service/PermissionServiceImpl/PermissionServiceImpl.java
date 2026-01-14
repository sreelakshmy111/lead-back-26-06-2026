package com.project.Permission.of.lead.service.PermissionServiceImpl;

import com.project.Permission.of.lead.dto.PermissionDto;
import com.project.Permission.of.lead.entity.Permission;
import com.project.Permission.of.lead.mapper.PermissionMapper;
import com.project.Permission.of.lead.repository.PermissionRepository;
import com.project.Permission.of.lead.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {


   private  PermissionRepository permissionRepo;

    @Override
    public PermissionDto setPermission(PermissionDto permissionDto) {

        Permission  permission = PermissionMapper.mapToPermission(permissionDto);
        Permission savedPermission = permissionRepo.save(permission);

        // 3️⃣ Map saved entity back to DTO and return
        return PermissionMapper.mapToPermissionDto(savedPermission);

    }
}

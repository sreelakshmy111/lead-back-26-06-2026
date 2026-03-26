package com.project.Permission.of.lead.service.UserRoleServiceImpl;

import com.project.Permission.of.lead.dto.UserRoleDto;
import com.project.Permission.of.lead.entity.UserRole;
import com.project.Permission.of.lead.mapper.UserRoleMapper;
import com.project.Permission.of.lead.repository.RoleRepository;
import com.project.Permission.of.lead.repository.UserRepository;
import com.project.Permission.of.lead.repository.UserRolerepository;
import com.project.Permission.of.lead.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

@Autowired
private final UserRepository userRepo;

@Autowired
private final RoleRepository roleRepo;

@Autowired
private final UserRolerepository userRoleRepo;

    public UserRoleDto assignRoleToUser(@RequestBody UserRoleDto dto) {
        var user = userRepo.findByUid(dto.getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 2️⃣ Map to UserRole entity
        UserRole userRole = UserRoleMapper.mapToUserRole(user,role);

        // 3️⃣ Save it to DB
        userRoleRepo.save(userRole);

        // 4️⃣ Return DTO
        return new UserRoleDto(dto.getUid(), dto.getRoleId());
    }

}

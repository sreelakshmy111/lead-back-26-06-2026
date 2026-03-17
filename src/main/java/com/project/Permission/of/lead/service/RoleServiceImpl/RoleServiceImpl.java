package com.project.Permission.of.lead.service.RoleServiceImpl;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.RoleMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolerepository userRolerepository;




    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;  // ✅ add this

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Roles createRole = RoleMapper.mapToRole(roleDto);
        Roles savedRole = roleRepo.save(createRole);
        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Roles> roles=roleRepo.findAll();
        return roles.stream().map(r->RoleMapper.mapToRoleDto(r)).collect(Collectors.toList());
    }

    /// Fetch exist employee roles..............................................................

    @Override
    public List<RoleDto> getExistRolesOfEmployee(String eid, String buid, String empId) {

        Enterprise e=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bu=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()-> new RuntimeException("bussiness unit not found"));


        PersonalManagement p=personalRepository.findByEmpId(empId).
                orElseThrow(()-> new RuntimeException("employeee not found"));

        Users user = userRepository.findByEmail(p.getEmail());


        List<UserRole> userRoles = userRolerepository.findByUser(user);



        return userRoles.stream()
                .map(ur -> {

                    RoleDto dto = new RoleDto();

                    dto.setRoleId(ur.getRole().getRoleId());
                    dto.setRoleName(ur.getRole().getRoleName());

                    return dto;

                })
                .toList();


    }






    /// DELETE ROLES OF EMPLOYEEE....................................................

    @Override
    public void deleteUserRoles(String eid, String buid, String employeeId, List<Long> roleIds) {

        PersonalManagement p=personalRepository.findByEmpId(employeeId).orElseThrow(()-> new RuntimeException("EMPLOYEE NOT FOUND"));

        Users user=userRepository.findByEmail(p.getEmail());

        if(user==null){
            throw new RuntimeException("USER NOT FOUND");
        }

        for (Long roleId : roleIds) {

            Roles role = roleRepo.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));

            userRolerepository.deleteRoles(user, roleIds);
        }



    }


}

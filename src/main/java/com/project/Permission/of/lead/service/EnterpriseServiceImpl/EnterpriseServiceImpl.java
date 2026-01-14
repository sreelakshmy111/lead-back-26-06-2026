package com.project.Permission.of.lead.service.EnterpriseServiceImpl;

import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.Address;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.EnterpriseMapper;
import com.project.Permission.of.lead.repository.AddressRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.UserRepository;
import com.project.Permission.of.lead.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.Permission.of.lead.mapper.EnterpriseMapper.mapToEnterpriseDto;

@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

@Autowired
private EnterpriseRepostory enterpriseRepo;

@Autowired
private UserRepository userRepo;

@Autowired
private AddressRepository addressRepo;

@Autowired
private JdbcTemplate jdbcTemplate;

// create a enterprise..........................................................

    @Override

    public EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser) {

        // 1️⃣ Check if logged-in user has ENTERPRISE_ADMIN role

        boolean isAdmin = loggedInUser.getUserRoles().stream()
                .anyMatch(userRole -> "ENTERPRISE_ADMIN".equals(userRole.getRole().getRole_name()));

        if (!isAdmin) {
            throw new RuntimeException("Only role with ENTERPRISE_ADMIN can create enterprise");
        }

        // 2️⃣ Fetch address ID from DTO and validate

//        Long addressId = enterpriseDto.getAddressId();
//        if (addressId == null) {
//            throw new RuntimeException("Address must be provided");
//        }
//
//        // Optional: verify address exists in DB
//        if (!addressRepo.existsById(addressId)) {
//            throw new RuntimeException("Address not found");
//        }

        // 3️⃣ Check if the enterprise already exists for this admin

        if (enterpriseRepo.existsByCreatedBy(loggedInUser.getUser_id())) {
            throw new RuntimeException("An Enterprise Admin can create only one enterprise");
        }

        // 4️⃣ Map DTO → Entity using IDs
        Enterprise enterprise = EnterpriseMapper.mapToEnterprise(
                enterpriseDto,
                loggedInUser.getUser_id(), // pass creator ID
                null                       // updatedBy is null initially
        );


        String eid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[] {"ENTERPRISE"},
                String.class
        );



        enterprise.setActive(true);
        enterprise.setEid(eid);
        // 5️⃣ Save entity
        Enterprise savedEnterprise = enterpriseRepo.save(enterprise);

        // 6️⃣ Map Entity → DTO and return
        return EnterpriseMapper.mapToEnterpriseDto(savedEnterprise);


    }

//    Update enterprise by eid..........................................
    @Override

    public EnterpriseDto updateEnterprise(String eid, EnterpriseDto updateEnterpriseDto, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepo.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not exist"));


        enterprise.setId(updateEnterpriseDto.getId());
        enterprise.setName(updateEnterpriseDto.getName());
        enterprise.setDescription(updateEnterpriseDto.getDescription());
        enterprise.setIndustry(updateEnterpriseDto.getIndustry());
        enterprise.setLegalFormEnum(updateEnterpriseDto.getLegalFormEnum());
        enterprise.setIsdCode(updateEnterpriseDto.getIsdCode());
//        enterprise.setAddress(updateEnterpriseDto.getAddress());


        // Update address if provided....................

        if (updateEnterpriseDto.getAddressId() != null) {
            // Optional: check if the address exists
            if (!addressRepo.existsById(updateEnterpriseDto.getAddressId())) {
                throw new RuntimeException("Address not found");
            }

            // Update enterprise with the new address ID
            enterprise.setAddressId(updateEnterpriseDto.getAddressId());
        }

        enterprise.setContactEmail(updateEnterpriseDto.getContactEmail());
        enterprise.setContactPhone(updateEnterpriseDto.getContactPhone());
        enterprise.setActive(updateEnterpriseDto.isActive());

        enterprise.setUpdatedAt(LocalDateTime.now());
        enterprise.setUpdatedBy(loggedInUser.getUser_id());

        // 5️⃣ Save (triggers @PreUpdate -> updatedAt set automatically)
        Enterprise updated = enterpriseRepo.save(enterprise);

        // 6️⃣ Map to DTO
        return EnterpriseMapper.mapToEnterpriseDto(updated);




    }



//    display enterprises..............................
    @Override
    @PreAuthorize("hasAnyRole('ENTERPRISE_ADMIN', 'BUSINESS_ADMIN')")
    public List<EnterpriseDto> getAll() {
        List<Enterprise> enterprise=enterpriseRepo.findAll();
        return enterprise.stream().map(enterprise1 -> EnterpriseMapper.mapToEnterpriseDto(enterprise1)).collect(Collectors.toList());
    }



//    Read enterprise by id............................................

    @Override
    public EnterpriseDto getenterpriseByEid(String enterpriseId) {

        Enterprise enterprise= enterpriseRepo.findByEid(enterpriseId).
               orElseThrow(() -> new RuntimeException("Enterprise not  exists"));

        return EnterpriseMapper.mapToEnterpriseDto(enterprise);




    }


}

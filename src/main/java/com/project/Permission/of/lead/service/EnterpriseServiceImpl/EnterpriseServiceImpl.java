package com.project.Permission.of.lead.service.EnterpriseServiceImpl;

import com.project.Permission.of.lead.dto.CheckUpDto;
import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.EnterpriseMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.EmployeeDraftService;
import com.project.Permission.of.lead.service.EnterpriseService;
import com.project.Permission.of.lead.service.PersonalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
private RegionRepository regionRepository;

@Autowired
private JdbcTemplate jdbcTemplate;

@Autowired
private BussinessUnitRepository bussinessUnitRepository;

@Autowired
private PersonalManagementService personalManagementService;

@Autowired
private EmployeeDraftService employeeDraftService;


@Autowired
private EmployeeDraftRepository employeeDraftRepository;

@Autowired
private PersonalRepository personalRepository;

@Autowired
private TeritoryRepoitory teritoryRepoitory;

// create a enterprise..........................................................

    @Override

    public EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser) {

        // 1️⃣ Check if logged-in user has ENTERPRISE_ADMIN role

        boolean isAdmin = loggedInUser.getUserRoles().stream()
                .anyMatch(userRole -> "ENTERPRISE_ADMIN".equals(userRole.getRole().getRoleName()));

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

        if (enterpriseRepo.existsByCreatedBy(loggedInUser.getUid())) {
            throw new RuntimeException("An Enterprise Admin can create only one enterprise");
        }

        System.out.println("logged in user"+loggedInUser.getEmail());

        // 4️⃣ Map DTO → Entity using IDs
        Enterprise enterprise = EnterpriseMapper.mapToEnterprise(
                enterpriseDto,
                loggedInUser.getUid(), // pass creator ID
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



        // calling move the data from employee draft table to employee table method....
        employeeDraftService.moveEmployeeDraft(loggedInUser.getEmail(),eid);


        // 6️⃣ Map Entity → DTO and return
        return EnterpriseMapper.mapToEnterpriseDto(savedEnterprise);


    }


    ///  Check enterprise exists..................................................................




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
        enterprise.setUpdatedBy(loggedInUser.getUid());

        // 5️⃣ Save (triggers @PreUpdate -> updatedAt set automatically)
        Enterprise updated = enterpriseRepo.save(enterprise);

        // 6️⃣ Map to DTO
        return EnterpriseMapper.mapToEnterpriseDto(updated);




    }

//    @Override
//    public boolean isEnterpriseCeated(Users loggedInUser) {
//        String email= loggedInUser.getEmail();
//        Users user=userRepo.findByEmail(email);
//        if(user==null){
//            throw new RuntimeException("user not found");
//        }
//
//        return enterpriseRepo.existsByCreatedBy(user.getUser_id());
//    }

    @Override
    public CheckUpDto checkStatus(Users loggedInUser) {

        // get employee using email

        Users u=userRepo.findByEmail(loggedInUser.getEmail());

        if(u==null){
            throw new RuntimeException("User not found");
        }
//        EmployeeDraft employee =
//                employeeDraftRepository.findByEmail(loggedInUser.getEmail()).orElseThrow(()-> new RuntimeException("Employee draft person not found"));

//        EmployeeDraft draft = employeeDraftRepository.findByEmail(loggedInUser.getEmail()).orElse(null);
//
//
//
//        if (draft != null) {
//            return new CheckUpDto(false, false);
//        }

        // 🔥 Check personal table
        PersonalManagement personal = personalRepository.findByEmail(loggedInUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String eid = personal.getEid();

        System.out.println("EMAIL: " + loggedInUser.getEmail());
        System.out.println("EID from personal: [" + eid + "]");
        System.out.println("Exists in enterprise: " + enterpriseRepo.existsByEid(eid));

        // ✅ Safe check
        if (eid == null || eid.isBlank()) {
            return new CheckUpDto(false, false);
        }

        boolean enterpriseCreate = enterpriseRepo.existsByEid(eid);

        boolean bussinessCreate = false;

        if (enterpriseCreate) {
            List<BussinessUnit> buList =
                    bussinessUnitRepository.findByEnterpriseId(eid);

            bussinessCreate = buList != null && !buList.isEmpty();
        }

        return new CheckUpDto(enterpriseCreate, bussinessCreate);

    }


    /// CHECK ENTERPRISE WHEATHER THE ALREADY EXIST.........................
    @Override
    public ResponseEntity<EnterpriseDto> checkEnterpriseExist(Users loggedInUser) {
        Enterprise enterprise=enterpriseRepo.findByCreatedBy(loggedInUser.getUid());
        if(enterprise==null){
            return ResponseEntity.notFound().build();
        }

        EnterpriseDto e=EnterpriseMapper.mapToEnterpriseDto(enterprise);

        return ResponseEntity.ok(e);
    }


    //    display enterprises..............................
    @Override
    @PreAuthorize("hasAnyRole('ENTERPRISE_ADMIN', 'BUSSINESS_ADMIN','LEAD_ANALYST','HR MANAGER')")
    public List<EnterpriseDto> getAll(Users loggedInUser) {
//          String enterpriseId = loggedInUser.getEnterpriseId();
//        List<Enterprise> enterprise=enterpriseRepo.findAllByCreatedBy(loggedInUser.getUser_id());
//        return enterprise.stream().map(enterprise1 -> EnterpriseMapper.mapToEnterpriseDto(enterprise1)).collect(Collectors.toList());



//        boolean isEnterpriseAdmin = loggedInUser.getUserRoles().stream()
//                .anyMatch(userRole ->
//                        userRole.getRole().getRoleName().equals("ENTERPRISE_ADMIN")
//                );
//


        // ✅ ENTERPRISE ADMIN → full data
//        if (isEnterpriseAdmin) {
//
//            List<Enterprise> enterprises =
//                    enterpriseRepo.findAllByCreatedBy(loggedInUser.getUser_id());
//
//            return enterprises.stream()
//                    .map(EnterpriseMapper::mapToEnterpriseDto)
//                    .collect(Collectors.toList());
//        }


        String email= loggedInUser.getEmail();

        /// get the logged in user details from employeeee....................

        PersonalManagement emp=personalRepository.findByEmail(email).
                orElseThrow(()-> new RuntimeException("employee not found"));

        System.out.println("logged in user details"+emp);

        String enterpriseId=emp.getEid();

        List<Enterprise> enterprise= Collections.singletonList(enterpriseRepo.findByEid(enterpriseId).orElseThrow(() -> new RuntimeException("enterprise not found")));


        return enterprise.stream().map(a->EnterpriseMapper.mapToEnterpriseDto(a)).collect(Collectors.toList());




        // BUSINESS ADMIN

        // BUSINESS ADMIN

//        boolean isBussinessAdmin=loggedInUser.getUserRoles().stream().
//                anyMatch(userRole -> userRole.getRole().getRoleName().equals("BUSSINESS_ADMIN"));
//
//        if(isBussinessAdmin){
//
//
//
//        }

//        List<BussinessUnit> units = bussinessUnitRepository.findAll();
//
//        if (units.isEmpty()) {
//            throw new RuntimeException("No Business Unit found");
//        }
//
//        String enterpriseId = units.get(0).getEnterpriseId();
//
//        Enterprise enterprise = enterpriseRepo.findByEid(enterpriseId)
//                .orElseThrow(() -> new RuntimeException("Enterprise not found"));
//
//        return List.of(
//                EnterpriseMapper.mapToEnterpriseDto(enterprise)
//        );
    }



//    Read enterprise by id............................................

    @Override
    public EnterpriseDto getenterpriseByEid(String enterpriseId) {

        Enterprise enterprise= enterpriseRepo.findByEid(enterpriseId).
               orElseThrow(() -> new RuntimeException("Enterprise not  exists"));

        return EnterpriseMapper.mapToEnterpriseDto(enterprise);




    }


}

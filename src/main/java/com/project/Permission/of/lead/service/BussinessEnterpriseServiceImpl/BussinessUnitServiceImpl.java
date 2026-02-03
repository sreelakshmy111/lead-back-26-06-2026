package com.project.Permission.of.lead.service.BussinessEnterpriseServiceImpl;

import com.project.Permission.of.lead.dto.BussinessUnitDto;
import com.project.Permission.of.lead.dto.LeadStatusCustomDto;
import com.project.Permission.of.lead.entity.Address;
import com.project.Permission.of.lead.entity.BussinessUnit;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.BussinessUnitMapper;
import com.project.Permission.of.lead.repository.AddressRepository;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.LeadStatusCustomRepository;
import com.project.Permission.of.lead.service.BussinessUnitService;
import com.project.Permission.of.lead.service.LeadStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BussinessUnitServiceImpl implements BussinessUnitService {

   @ Autowired
    private EnterpriseRepostory enterpriseRepo;

   @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BussinessUnitRepository bussinessRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private LeadStatusCustomRepository leadStatusCustomRepository;

    @Autowired
    private LeadStatusService leadStatusService;


    ///  CREATE BUSSINESS UNIT..........................................................

    @Override
    public BussinessUnitDto createBussinessUnit(String eid, BussinessUnitDto bussinessUnitDto, Users loggedInUser) {

        // Fetch the enterprise
        Enterprise enterprise = enterpriseRepo.findByEid(eid)
                .orElseThrow(() -> new RuntimeException("Enterprise Not Found"));



//        // Fetch Address if provided..........
//        Long addressId = bussinessUnitDto.getAddressId();
//        if (addressId == null) {
//            throw new RuntimeException("Address must be provided");
//        }
//
//        // Optional: verify address exists in DB
//        if (!addressRepo.existsById(addressId)) {
//            throw new RuntimeException("Address not found");
//        }

        // Generate BU ID from DB function
        String buid = jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"BUSSINESS_UNIT"},
                String.class
        );
        System.out.println("Generated BU ID: " + buid);


        // 3️⃣ Map DTO -> Entity using current user
        BussinessUnit unit = BussinessUnitMapper.mapToBussinessEnterprise(bussinessUnitDto, loggedInUser.getUser_id(),null);

        // 4️⃣ Link Enterprise ID

        unit.setEnterpriseId(enterprise.getEid());
        unit.setBuid(buid);
        unit.setActive(true);

        // 5️⃣ Save entity
        BussinessUnit saved = bussinessRepo.save(unit);

        // Call procedure (fire-and-forget)
        jdbcTemplate.update(
                "CALL copy_default_lead_stages_proc(?, ?, ?)",
                eid,
                buid,
                loggedInUser.getUser_id()
        );

//        leadStatusService.leadStatus(eid, buid, new LeadStatusCustomDto(), loggedInUser);


        // 6️⃣ Map Entity -> DTO
        return BussinessUnitMapper.mapToBussinessEnterpriseDto(saved);

    }


//    update the bussiness unit...........................................
//
    @Override
    public BussinessUnitDto updateBussinessEnterprise(Long enterpriseId, Long businessunitId, BussinessUnitDto bussinessUnitDto,Users currentUser) {
        // 1️⃣ Fetch the enterprise
        Enterprise enterprise = enterpriseRepo.findById(enterpriseId)
                .orElseThrow(() -> new RuntimeException("Enterprise not found"));

        // 2️⃣ Fetch the business unit
        BussinessUnit bu = bussinessRepo.findById(businessunitId)
                .orElseThrow(() -> new RuntimeException("Business Unit not found"));

        // 3️⃣ Check that the BU belongs to the enterprise
        if (!bu.getEnterpriseId().equals(enterprise.getId())) {
            throw new RuntimeException("Business Unit does not belong to this Enterprise");
        }


        bu.setName(bussinessUnitDto.getName());
        bu.setDescription(bussinessUnitDto.getDescription());
        bu.setIndustry(bussinessUnitDto.getIndustry());
        bu.setLegalFormEnum(bussinessUnitDto.getLegalForumEnum());
        bu.setAddressId(bussinessUnitDto.getAddressId());
        bu.setContactEmail(bussinessUnitDto.getContactEmail());
        bu.setContactPhone(bussinessUnitDto.getContactPhone());
        bu.setActive(bussinessUnitDto.isActive());
        bu.setEnterpriseId(bussinessUnitDto.getEnterpriseId());

// audit fields
        bu.setUpdatedAt(LocalDateTime.now());
        bu.setUpdatedBy(currentUser.getUser_id());

        // 4️⃣ Update BU fields from DTO


        // 5️⃣ Save updated BU
        BussinessUnit updatedBU = bussinessRepo.save(bu);

        // 6️⃣ Map to DTO and return
        return BussinessUnitMapper.mapToBussinessEnterpriseDto(updatedBU);
    }


//    .........Update...........................................


    @Override
    public BussinessUnitDto updateBussinessUnit(BussinessUnitDto bussinessUnitDto, Users loggedUser, String buid, String eid) {
       Enterprise enterprise=enterpriseRepo.findByEid(eid).
               orElseThrow(() -> new RuntimeException("Enterprise Not Found"));

       BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).
               orElseThrow(()-> new RuntimeException("Business Unit not found"));

       if(!bussinessUnit.getEnterpriseId().equals(eid)){
           throw new RuntimeException("Business Unit does not belong to this Enterprise");
       }

       bussinessUnit.setName(bussinessUnitDto.getName());
       bussinessUnit.setDescription(bussinessUnitDto.getDescription());
       bussinessUnit.setIndustry(bussinessUnitDto.getIndustry());
       bussinessUnit.setLegalFormEnum(bussinessUnitDto.getLegalForumEnum());
       bussinessUnit.setAddressId(bussinessUnitDto.getAddressId());
       bussinessUnit.setContactEmail(bussinessUnitDto.getContactEmail());
       bussinessUnit.setIsdCode(bussinessUnitDto.getIsdCode());
       bussinessUnit.setContactPhone(bussinessUnitDto.getContactPhone());
       bussinessUnit.setActive(bussinessUnitDto.isActive());
       bussinessUnit.setUpdatedAt(LocalDateTime.now());
       bussinessUnit.setUpdatedBy(loggedUser.getUser_id());

       BussinessUnit updated=bussinessRepo.save(bussinessUnit);
       return BussinessUnitMapper.mapToBussinessEnterpriseDto(updated);

    }

    @Override
    public void linkAddress(Long enterpriseId, Long bussiessunitId, Long addressId) {
        Enterprise enterprise=enterpriseRepo.findById(enterpriseId).
                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessRepo.findById(bussiessunitId).
                orElseThrow(()-> new RuntimeException("Business Unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(enterpriseId)){
            throw new RuntimeException("Business Unit does not belong to this Enterprise");
        }

        Address address=addressRepo.findById(addressId).
                orElseThrow(() -> new RuntimeException("Address Not Found"));

        bussinessUnit.setAddressId(addressId);
        bussinessRepo.save(bussinessUnit);






    }



//    @PreAuthorize("hasRole('ENTERPRISE_ADMIN')")
//    public BussinessUnitDto createBussinessEnterprise(BussinessUnitDto bussinessUnitDto, Users loggedInUser) {
//
//        BussinessUnit bussinessUnit = BussinessUnitMapper.mapToBussinessEnterprise(bussinessUnitDto, loggedInUser);
//        BussinessUnit saved=bussinessRepo.save(bussinessUnit);
//        return BussinessUnitMapper.mapToBussinessEnterpriseDto(saved);
//
//
//    }

//    @Override
//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public BussinessUnitDto updateBussinessEnterprise(Long id, BussinessUnitDto bussinessUnitDto) {
//
////        / 1️⃣ Fetch existing entity
//        BussinessUnit existing = bussinessRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("BusinessEnterprise not found"));
//
//        // 2️⃣ Update only editable fields
//        if(bussinessUnitDto.getId()!=null) existing.setId(bussinessUnitDto.getId());
//        if(bussinessUnitDto.getName()!=null) existing.setName(bussinessUnitDto.getName());
//        if(bussinessUnitDto.getDescription()!=null) existing.setDescription(bussinessUnitDto.getDescription());
//
//        if(bussinessUnitDto.getIndustry()!=null) existing.setIndustry(bussinessUnitDto.getIndustry());
//        if(bussinessUnitDto.getAddress()!=null) existing.setAddress(bussinessUnitDto.getAddress());
//
//        if(bussinessUnitDto.getContactEmail()!=null) existing.setContactEmail(bussinessUnitDto.getContactEmail());
//
//        if(bussinessUnitDto.getContactPhone()!=null) existing.setContactPhone(bussinessUnitDto.getContactPhone());
//
//        existing.setUpdatedAt(LocalDateTime.now()); // update timestamp
//
//        // 3️⃣ Save updated entity
//        BussinessUnit updated = bussinessRepo.save(existing);
//
//        // 4️⃣ Map back to DTO and return
//        return BussinessUnitMapper.mapToBussinessEnterpriseDto(updated);
//
//
//
//    }

//    @Override
//    public List<BussinessUnitDto> getAll() {
//       List<BussinessUnit> bussinessEnterprises=bussinessRepo.findAll();
//       return bussinessEnterprises.stream().map(bussinessEnterprise -> BussinessUnitMapper.mapToBussinessEnterpriseDto(bussinessEnterprise)).collect(Collectors.toList());
//    }


// GET Bussinesunits.......................................................................

    @Override
    public List<BussinessUnitDto> getBussinessEnterpriseById(String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid).
                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));



        List<BussinessUnit> bussinessUnits=bussinessRepo.findByEnterpriseId(eid);
        List<BussinessUnitDto> bussinessUnitDtos=bussinessUnits.stream().map(bu->BussinessUnitMapper.mapToBussinessEnterpriseDto(bu)).collect(Collectors.toList());

        return bussinessUnitDtos;

    }

    @Override
    public BussinessUnitDto getBussinessByIdEnterpriseById(String eid, String buid) {

        BussinessUnit bussinessUnit = bussinessRepo
                .findByEnterpriseIdAndBuid(eid, buid)
                .orElseThrow(() -> new RuntimeException("Business Unit Not Found"));


        BussinessUnitDto bussinessUnitDto=BussinessUnitMapper.mapToBussinessEnterpriseDto(bussinessUnit);
        return  bussinessUnitDto;




    }



}

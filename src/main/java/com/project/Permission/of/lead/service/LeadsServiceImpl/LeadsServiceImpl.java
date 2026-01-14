package com.project.Permission.of.lead.service.LeadsServiceImpl;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.LeadsMapper;
import com.project.Permission.of.lead.mapper.TeritoryMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.LeadsService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeadsServiceImpl implements LeadsService {

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TeritoryRepoitory teritoryRepoitory;
    @Autowired
    private PersonalRepository personalRepository;

    ///  CREATE LEAD...................................................................................

    @Override
    public LeadsDto createLead(LeadsDto leadsDto, Users loggedInUser, String eid, String buid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("busssiness unit not belong to the enterprise(lead)");
        }

        String leadId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                                                 new Object[] {"LEAD"},
                                                  String.class);

        Leads leads= LeadsMapper.mapToLeads(leadsDto,loggedInUser.getUser_id(),eid,buid);
        leads.setActive(true);
        leads.setLid(leadId);
        leadsRepository.save(leads);
        return LeadsMapper.mapToLeadsDto(leads);

    }

    /// GET ALL LEADS..................................................

    @Override
    public List<LeadsDto> getallLeads(String eid, String buid, Users loggedInUser, UserPrinciple userPrinciple) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("busssiness unit not belong to the enterprise(lead)");
        }



        List<Leads> leads=leadsRepository.findByEidAndBuid(eid,buid);
        return leads.stream().map(l->LeadsMapper.mapToLeadsDto(l)).collect(Collectors.toList());

//        return leadsRepository.getAllLeadsWithNames(eid, buid);
    }

    @Override
    public LeadsDto assignEmployee(LeadsDto leadsDto, String eid, String buid, String lid, Users loggedUser, UserPrinciple userPrinciple) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("busssiness unit not belong to the enterprise(lead)");
        }

        Leads leads=leadsRepository.findByLid(lid).
                orElseThrow(()->new RuntimeException("Lead not found"));



        leads.setEmployeeId(leadsDto.getEmployeeId());
        leadsRepository.save(leads);
        return  LeadsMapper.mapToLeadsDto(leads);



    }

    /// ASSIGN territories to lead...........................................................

    @Override
    public LeadsDto assignTerritoryToLead(LeadsDto leadsDto, String eid, String buid,String lid, String tid, Users loggedInUser, UserPrinciple userPrinciple) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("bussinessunit not found"));

        Leads leads=leadsRepository.findByLid(lid).orElseThrow(()-> new RuntimeException("lead not exists"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussinessuunit not belong to this enterprise");
        }

        Teritory teritory=teritoryRepoitory.findByTid(tid).
                orElseThrow(()->new RuntimeException("territory not found"));

        if(!teritory.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("territories not belong to this bussiness unit");
        }

        leads.setTerritoryId(tid);

        Leads l=leadsRepository.save(leads);
        return LeadsMapper.mapToLeadsDto(l);


    }


    /// ASSIGN EMPLOYE TO TERRITORY....................................................................
    @Override
    public LeadsDto assignEmployeeToLead(LeadsDto leadsDto, String eid, String buid, String lid, String tid, String empid, Users loggedInUser, UserPrinciple userPrinciple) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("bussinessunit not found"));

        Leads leads=leadsRepository.findByLid(lid).orElseThrow(()-> new RuntimeException("lead not exists"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussinessuunit not belong to this enterprise");
        }

        Teritory teritory=teritoryRepoitory.findByTid(tid).
                orElseThrow(()->new RuntimeException("territory not found"));


        if(!teritory.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("territory not belong to this bussieness unit");
        }

        PersonalManagement employee=personalRepository.findByEmpId(empid).
                orElseThrow(()->new RuntimeException("employeee not found"));

        if (!employee.getBuid().equals(buid)) {
            throw new RuntimeException("employee not belong to this business unit");
        }
        if(!employee.getTerritoryId().contains(tid)){
            throw new RuntimeException("employee not belong to ");

        }
        leads.setEmployeeId(empid);
        Leads lead=leadsRepository.save(leads);
        return LeadsMapper.mapToLeadsDto(lead);

    }


    /// Leads at a glnace....................................................................
    @Override
    public Page<LeadsDto> findLeadByGlance(String eid, String buid, int pageNo) {

        Pageable pageable= PageRequest.of(pageNo,15,Sort.by("createdAt").descending());

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).orElseThrow(()-> new RuntimeException("bussiness unit not found"));

       Page<Leads> leads= leadsRepository.findByEidAndBuid(eid,buid,pageable);

       return leads.map(l->LeadsMapper.mapToLeadsDto(l));
    }


}

package com.project.Permission.of.lead.service.taskFollow;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.dto.TaskFollowUpDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.LeadsMapper;
import com.project.Permission.of.lead.mapper.TaskFollowUpMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.LeadsRepository;
import com.project.Permission.of.lead.repository.TaskFollowUPRepository;
import com.project.Permission.of.lead.service.TaskFollowUpService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class TaskFollowUpImpl implements TaskFollowUpService {

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private TaskFollowUPRepository taskFollowUPRepository;

    @Override
    public List<TaskFollowUpDto> getFollowUps( String buid, String lid, Users loggedInUser) {
//        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));


        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("bussinessunit not found"));

       Leads leads=leadsRepository.findByLid(lid).orElseThrow(()-> new RuntimeException("lead not exists"));

       List<TaskFollowUp> followUp=taskFollowUPRepository.findByBussinessUnitIdAndLeadIdOrderByCreatedDateDesc(buid,lid);
       return followUp.stream().map(a->TaskFollowUpMapper.mapToTaskFollowUpDto(a)).collect(Collectors.toList());

    }
//
//    /// CREATE FOLLOWUPSS........................................................................
//    ///
//    @Override
//    public TaskFollowUpDto createFollowUp(String eid, String buid, String lid, TaskFollowUpDto taskFollowUpDtoDto, Users loggedInUser) {
//        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));
//
//
//        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
//                orElseThrow(()->new RuntimeException("bussinessunit not found"));
//
////        Leads leads=leadsRepository.findByLid(lid).orElseThrow(()-> new RuntimeException("lead not exists"));
//
//        if(!bussinessUnit.getEnterpriseId().equals(eid)){
//            throw new RuntimeException("bussinessuunit not belong to this enterprise");
//        }
//
//
//
//        Leads l=leadsRepository.findByEidAndBuidAndLid(eid,buid,lid);
//        l.setNextFollowUp(taskFollowUpDtoDto.getNextFollowUp());
//        l.setNote(taskFollowUpDtoDto.getNote());
//        Leads x=leadsRepository.save(l);
//        return TaskFollowUpMapper.mapToTaskFollowUpDto(l);
//    }
//
//



}

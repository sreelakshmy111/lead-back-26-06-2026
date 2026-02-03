package com.project.Permission.of.lead.service.LeadStatusCustom;

import com.project.Permission.of.lead.dto.LeadStatusCustomDto;
import com.project.Permission.of.lead.entity.BussinessUnit;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.LeadStatusCustom;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.LeadStatusMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.LeadStatusCustomRepository;
import com.project.Permission.of.lead.service.LeadStatusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeadStatusCustomImpl implements LeadStatusService {

    @Autowired
    private LeadStatusCustomRepository leadStatusCustomRepository;

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Override


    public LeadStatusCustomDto leadStatus(
            String eid,
            String buid,
            LeadStatusCustomDto leadStatusCustomDto,
            Users loggedInUser
    ) {

        enterpriseRepostory.findByEid(eid)
                .orElseThrow(() -> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(buid)
                .orElseThrow(() -> new RuntimeException("Business Unit not found"));

        if (!bussinessUnit.getEnterpriseId().equals(eid)) {
            throw new RuntimeException("Business Unit does not belong to the enterprise");
        }

        // ✅ FIXED LINE
        if (leadStatusCustomRepository.existsByEnterpriseIdAndBussinessUnitId(eid, buid)) {
            throw new RuntimeException("Lead stages already initialized");
        }


        LeadStatusCustomDto response = new LeadStatusCustomDto();
        response.setEnterpriseId(eid);
        response.setBussinessUnitId(buid);
//        response.setMessage(count + " lead stages initialized successfully");

        return response;
    }


    /// Get all leadststges..........................................................

    @Override
    public List<LeadStatusCustomDto> getLeadStages(String eid, String buid, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to this entyerprise");
        }

       List<LeadStatusCustom> stages=leadStatusCustomRepository.findByEnterpriseIdAndBussinessUnitId(eid,buid);

        return stages.stream().map(s-> LeadStatusMapper.mapToLeadStatusCustomDto(s)).collect(Collectors.toList());

    }

    // add new stages.......................................................................

    @Override
    public LeadStatusCustomDto createNewStage(String eid, String buid, LeadStatusCustomDto leadStatusCustomDto, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("enterprise not exists"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to this enterprise");
        }

        LeadStatusCustom stages=LeadStatusMapper.mapToLeadStatusCustom(eid,buid,loggedInUser,leadStatusCustomDto);
        Long newSequence =
                leadStatusCustomRepository
                        .findTopByEnterpriseIdAndBussinessUnitIdOrderByStatusSequenceDesc(eid, buid)
                        .map(LeadStatusCustom::getStatusSequence)
                        .orElse(0L) + 1;
        stages.setStatusSequence(newSequence);

        LeadStatusCustom l=leadStatusCustomRepository.save(stages);
        return LeadStatusMapper.mapToLeadStatusCustomDto(l);
    }

    @Override
    public LeadStatusCustomDto deleteStage(String eid, String buid, Long stageId) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to this enterprise");
        }

        // ✅ FIX HERE
        LeadStatusCustom stage =
                leadStatusCustomRepository
                        .findByIdAndEnterpriseIdAndBussinessUnitId(stageId, eid, buid)
                        .orElseThrow(() ->
                                new RuntimeException("stage not found for this enterprise and business unit")
                        );
        Long deleteSequence=stage.getStatusSequence();

        leadStatusCustomRepository.deleteByIdAndEnterpriseIdAndBussinessUnitId(stageId,eid,buid);

        leadStatusCustomRepository.resequenceAfterDelete(deleteSequence,eid,buid);

        return LeadStatusMapper.mapToLeadStatusCustomDto(stage);
    }


    ///  LEAD STAGE EDIT...............................................................

    @Override
    public LeadStatusCustomDto editLeadsStage(String eid, String buid, Long stageId, LeadStatusCustomDto leadStatusCustomDto) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).orElseThrow(()-> new RuntimeException("busssiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to the enterptise unit");
        }

        LeadStatusCustom stages=leadStatusCustomRepository.findByIdAndEnterpriseIdAndBussinessUnitId(stageId,eid,buid).orElseThrow(()-> new RuntimeException("stages id not found in bussiness unit and enterprise id"));

        Long sequence=stages.getStatusSequence();

        stages.setStatusCode(leadStatusCustomDto.getStatusCode());
        stages.setStatusDescription(leadStatusCustomDto.getStatusDescription());
        stages.setStatusSequence(leadStatusCustomDto.getStatusSequence());
        leadStatusCustomRepository.save(stages);
        return LeadStatusMapper.mapToLeadStatusCustomDto(stages);
    }

    public void moveStage(String eid, String buid, Long stageId, String direction) {

        LeadStatusCustom current =
                leadStatusCustomRepository.findById(stageId)
                        .orElseThrow(() -> new RuntimeException("Stage not found"));

        Optional<LeadStatusCustom> swapOpt;

        if ("UP".equalsIgnoreCase(direction)) {
            swapOpt =
                    leadStatusCustomRepository
                            .findTopByEnterpriseIdAndBussinessUnitIdAndStatusSequenceLessThanOrderByStatusSequenceDesc(
                                    eid, buid, current.getStatusSequence());
        } else {
            swapOpt =
                    leadStatusCustomRepository
                            .findTopByEnterpriseIdAndBussinessUnitIdAndStatusSequenceGreaterThanOrderByStatusSequenceAsc(
                                    eid, buid, current.getStatusSequence());
        }

        // already at top or bottom
        if (swapOpt.isEmpty()) {
            return;
        }

        LeadStatusCustom swapStage = swapOpt.get();

        // 🔁 SWAP SEQUENCE
        Long temp = current.getStatusSequence();
        current.setStatusSequence(swapStage.getStatusSequence());
        swapStage.setStatusSequence(temp);

        leadStatusCustomRepository.save(current);
        leadStatusCustomRepository.save(swapStage);
    }



    @Override
    public LeadStatusCustomDto findLeadByAsc(String eid, String buid, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to the enterprise");
        }

        LeadStatusCustom lead=leadStatusCustomRepository.findTopByEnterpriseIdAndBussinessUnitIdOrderByStatusSequenceAsc(eid,buid);
        return LeadStatusMapper.mapToLeadStatusCustomDto(lead);
    }

//    @Override
//    public LeadStatusCustomDto saveLeadStage(String eid, String buid, String lid, Users loggedInUser) {
//
//        Enterprise enterprise=enterpriseRepostory.findByEid(eid).orElseThrow(()-> new RuntimeException("enterprise not found"));
//
//        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).orElseThrow(()-> new RuntimeException("bussiness unit not found"));
//
//        if(!bussinessUnit.getEnterpriseId().equals(eid)){
//            throw new RuntimeException("bussiness unit not belong to the enterprise");
//        }
//
//        lead
//    }


}

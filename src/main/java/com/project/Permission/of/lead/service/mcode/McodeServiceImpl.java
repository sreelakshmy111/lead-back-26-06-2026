package com.project.Permission.of.lead.service.mcode;

import com.project.Permission.of.lead.dto.code.McodeDto;
import com.project.Permission.of.lead.dto.code.McodeValueDto;
import com.project.Permission.of.lead.entity.code.Mcode;
import com.project.Permission.of.lead.entity.code.McodeValue;
import com.project.Permission.of.lead.mapper.McodeMapper;
import com.project.Permission.of.lead.repository.code.McodeRepository;
import com.project.Permission.of.lead.repository.code.McodeValueRepository;
import com.project.Permission.of.lead.service.McodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class McodeServiceImpl implements McodeService {



    @Autowired
    private McodeRepository repo;

    @Autowired
    private McodeValueRepository  mcodeValueRepo;


    public McodeDto createMcodeMaster(McodeDto mcodeDto) {
        Mcode mcode = McodeMapper.mapToMcode(mcodeDto,repo);
        Mcode saveData = repo.save(mcode);
        return McodeMapper.mapToMcodeDto(saveData);

    }

//    @Override
//    public McodeDto getParentWithChildrenByCodeName(String codeName) {
//        return null;
//    }


//    public McodeDto getParentWithChildrenByCodeName(String codeName) {
//        // Fetch parent entity from repository
//        Mcode parent = repo.findByCodeName(codeName)
//                .orElseThrow(() -> new RuntimeException("Code not found"));
//
//        // Map to DTO (including children)
//        return McodeMapper.mapToMcodeDto(parent);
//    }




    public McodeDto getParentWithChildrenByCodeName(String codeName) {

        // Test fetching storeValue
        McodeValue value = mcodeValueRepo.findById(2L)
                .orElseThrow(() -> new RuntimeException("McodeValue not found"));
        System.out.println("storeValue: " + value.getStoreValue());


        // Split at '/' to get only the parent code
        String parentCode = codeName.split("/")[0]; // "YesNo"

        Mcode parent = repo.findParentWithChildrenByCodeName(parentCode)
                .orElseThrow(() -> new RuntimeException("Code not found"));
        return McodeMapper.mapToMcodeDto(parent);
    }

}

package com.project.Permission.of.lead.mapper;


import com.project.Permission.of.lead.dto.code.McodeValueDto;
import com.project.Permission.of.lead.entity.code.Mcode;
import com.project.Permission.of.lead.entity.code.McodeValue;
import com.project.Permission.of.lead.repository.code.McodeRepository;
import com.project.Permission.of.lead.repository.code.McodeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class McodeValueMapper {

@Autowired
    private  McodeValueRepository mcodeValueRepo;

@Autowired

    private  McodeRepository mcodeRepo;


    public  McodeValue mapToMcodeValue(McodeValueDto mcodeValueDto) {


        // Fetch the Mcode entity by ID
        Mcode code = mcodeRepo.findById(mcodeValueDto.getCodeId())
                .orElseThrow(() -> new RuntimeException("Mcode not found"));


        // Fetch the parent McodeValue entity if parentId is provided
        McodeValue parent = null;
        if (mcodeValueDto.getParentId()!= null) {
            parent = mcodeValueRepo.findById(mcodeValueDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent McodeValue not found"));
        }


        return new McodeValue(

                mcodeValueDto.getId(),
                mcodeValueDto.isActive(),
                mcodeValueDto.getCodeDescription(),
                mcodeValueDto.getCodeValue(),
                mcodeValueDto.isMandatory(),
                mcodeValueDto.getOrderPosition(),
                mcodeValueDto.getStoreValue(),
                code,
                parent


        );
    }

    public  McodeValueDto mapToMcodeValueDto(McodeValue mcodeValue) {

        return new McodeValueDto(
                mcodeValue.getId(),
                mcodeValue.isActive(),
                mcodeValue.getCodeDescription(),
                mcodeValue.getCodeValue(),
                mcodeValue.isMandatory(),
                mcodeValue.getOrderPosition(),
                mcodeValue.getStoreValue(),
                mcodeValue.getParent()!=null? mcodeValue.getParent().getId() :null,
                mcodeValue.getCode()!=null? mcodeValue.getCode().getId():null




        );
    }





}

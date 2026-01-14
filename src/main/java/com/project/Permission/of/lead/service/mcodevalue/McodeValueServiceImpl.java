package com.project.Permission.of.lead.service.mcodevalue;

import com.project.Permission.of.lead.dto.code.McodeValueDto;
import com.project.Permission.of.lead.entity.code.McodeValue;
import com.project.Permission.of.lead.mapper.McodeValueMapper;
import com.project.Permission.of.lead.repository.code.McodeValueRepository;
import com.project.Permission.of.lead.service.McodeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class McodeValueServiceImpl implements McodeValueService {

    @Autowired
    private McodeValueRepository  mcodeValueRepository;

    @Autowired
    private McodeValueMapper mcodeValueMapper;

    public McodeValueDto createMcodeChild(McodeValueDto mcodeValueDto) {

        McodeValue mcodeValue = mcodeValueMapper.mapToMcodeValue(mcodeValueDto);
        McodeValue saveChild=mcodeValueRepository.save(mcodeValue);
        return mcodeValueMapper.mapToMcodeValueDto(saveChild);

    }



}

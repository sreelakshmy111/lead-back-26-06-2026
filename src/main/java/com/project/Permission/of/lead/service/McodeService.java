package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.code.McodeDto;
import com.project.Permission.of.lead.entity.code.Mcode;

import java.util.Optional;

public interface McodeService {

    McodeDto createMcodeMaster(McodeDto mcodeDto);

    McodeDto getParentWithChildrenByCodeName(String codeName);
}

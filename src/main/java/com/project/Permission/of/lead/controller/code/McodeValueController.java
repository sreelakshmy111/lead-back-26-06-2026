package com.project.Permission.of.lead.controller.code;


import com.project.Permission.of.lead.dto.code.McodeDto;
import com.project.Permission.of.lead.dto.code.McodeValueDto;
import com.project.Permission.of.lead.service.mcode.McodeServiceImpl;
import com.project.Permission.of.lead.service.mcodevalue.McodeValueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("code_value")
public class McodeValueController {

    @Autowired
    private McodeValueServiceImpl service;


    @PostMapping("/store")
    public ResponseEntity<McodeValueDto> createMcodeChild(@RequestBody McodeValueDto mcodeValueDto) {

        McodeValueDto mcode=service.createMcodeChild(mcodeValueDto);
        return ResponseEntity.ok(mcode);

    }


}

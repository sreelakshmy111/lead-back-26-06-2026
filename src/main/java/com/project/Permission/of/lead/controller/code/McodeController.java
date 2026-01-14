package com.project.Permission.of.lead.controller.code;

import com.project.Permission.of.lead.dto.code.McodeDto;
import com.project.Permission.of.lead.entity.code.Mcode;
import com.project.Permission.of.lead.service.mcode.McodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("master")
public class McodeController {

    @Autowired
    private McodeServiceImpl service;

    @PostMapping("/store")
    public ResponseEntity<McodeDto> createMcodeMaster(@RequestBody McodeDto mcodeDto) {

        McodeDto mcode=service.createMcodeMaster(mcodeDto);
        return ResponseEntity.ok(mcode);

    }


//   / Get parent with children by codeName

@GetMapping("/code")
public McodeDto getParentByCodeName(@RequestParam("name") String codeName) {
    // Call the service method that fetches parent with children
    return service.getParentWithChildrenByCodeName(codeName);
}

}

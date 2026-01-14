package com.project.Permission.of.lead.repository.code;

import com.project.Permission.of.lead.entity.code.Mcode;
import com.project.Permission.of.lead.entity.code.McodeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface McodeValueRepository extends JpaRepository<McodeValue, Long> {

    List<McodeValue> findByParentCode(Mcode parent);
}

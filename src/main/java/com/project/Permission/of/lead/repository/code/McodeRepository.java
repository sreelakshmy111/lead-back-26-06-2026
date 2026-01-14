package com.project.Permission.of.lead.repository.code;

import com.project.Permission.of.lead.entity.code.Mcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface McodeRepository extends JpaRepository<Mcode,Long> {
//    Optional<Mcode> findByCodeName(String codeName);

//    @Query("SELECT m FROM Mcode m LEFT JOIN FETCH m.codeValues WHERE m.codeName = :codeName")
//    Optional<Mcode> findParentWithChildrenByCodeName(@Param("codeName") String codeName);


    @Query("SELECT m FROM Mcode m LEFT JOIN FETCH m.codeValues WHERE LOWER(m.codeName) = LOWER(:codeName)")
    Optional<Mcode> findParentWithChildrenByCodeName(@Param("codeName") String codeName);

}

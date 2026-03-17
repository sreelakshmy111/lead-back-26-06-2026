package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.PersonalManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<PersonalManagement,Long> {



    Optional <PersonalManagement> findByEmpId(String employeeId);

//    @Query("SELECT p FROM PersonalManagement p "
//            "WHERE p.eid = :eid ";
//
//    List<PersonalManagement> findByEid(
//            @Param("eid") String enterpriseId,
//
//    );

    List<PersonalManagement> findByEid(String enterpriseId);

   List<PersonalManagement> findByEidAndBuid(String enterpriseId, String bussinessUnitId);



//    @Query("""
//           SELECT p from PersonalManagement p
//            WHERE p.eid = :eid
//            AND p.buid = :buid
//            AND p.territoryId = :territoryId
//           """)
//    List<PersonalManagement> findByEidAndBuidAndTerritoryId(@Param("eid") String enterpriseId,
//                                                            @Param("buid") String bussinessUnitId,
//                                                            @Param("territoryId") String territoryId);
@Query(
        value = """
    SELECT *
    FROM employee p
    WHERE p.eid = :eid
      AND p.buid = :buid
      AND :tid = ANY (p.territory_id)
  """,
        nativeQuery = true
)
    List<PersonalManagement> findByEidAndBuidAndTerritory(
           @Param("eid") String enterpriseId,
           @Param("buid") String bussinessUnitId,
           @Param("tid") String territoryId);



@Query(value = " SELECT * FROM  employee where :tid=ANY(territory_id) AND buid = :buid ",nativeQuery = true)
List<PersonalManagement> findByAssignedTerritory(@Param("tid") String tid,
                                                 @Param("buid")String buid);

    boolean existsByEmail(String email);

    Optional<PersonalManagement> findByEmail(String email);


    @Query("""
SELECT e From PersonalManagement e
WHERE e.eid = :eid
AND
e.buid = :buid
AND
 (LOWER(CONCAT(e.firstName,'',COALESCE(e.middleName,''),'',e.lastName)) 
LIKE LOWER(CONCAT('%',:keyword,'%'))
OR LOWER(e.email) LIKE(CONCAT('%',:keyword,'%')))
""")
    List<PersonalManagement> searchEmployeeByEnterpriseAndBussinessUnit(@Param("keyword") String keyword, @Param("eid") String eid,@Param("buid") String buid);


//    List<PersonalManagement> findByEidAndBuidAndTerritory(String enterpriseId, String bussinessUnitId, String territoryId);
}

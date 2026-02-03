package com.project.Permission.of.lead.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.project.Permission.of.lead.entity.LeadStatusCustom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Transactional
public interface LeadStatusCustomRepository extends JpaRepository<LeadStatusCustom,Long> {

//    @Modifying
//    @Query(value = """
//    INSERT INTO lead_status_custom
//    (enterprise_id, bu_id, status_code, status_description, status_sequence, created_date, created_by)
//    SELECT :enterpriseId, :buId, code_value, code_description, order_position, NOW(), :userId
//    FROM m_code_value
//    WHERE code_id = (SELECT id FROM m_code WHERE code_name = 'lead_stages')
//    ORDER BY order_position
//    """, nativeQuery = true)
//    int copyDefaultStages(
//            @Param("enterpriseId") String enterpriseId,
//            @Param("buId") String buId,
//            @Param("userId") Long userId
//    );


//    boolean existsByEidAndBuid(String eid, String buid);

//    boolean existsByEnterpriseIdAndBuId(String eid, String buid);

    boolean existsByEnterpriseIdAndBussinessUnitId(String eid, String buid);





    List<LeadStatusCustom> findByEnterpriseIdAndBussinessUnitId(String eid, String buid);

//    Long findMaxSequence(String eid, String buid);

//    Optional<LeadStatusCustom>
//    findTopByEnterpriseIdAndBuIdOrderByStatusSequenceDesc(
//            String enterpriseId,
//            String buId
//    );

Optional<LeadStatusCustom> findByIdAndEnterpriseIdAndBussinessUnitId(
        Long id,
        String enterpriseId,
        String bussinessUnitId
);

    Optional<LeadStatusCustom> findTopByEnterpriseIdAndBussinessUnitIdOrderByStatusSequenceDesc(String enterpriseId, String bussinessunitId);

//    LeadStatusCustom deleteByEidAndBuid(String eid, String buid);

//    void deleteByIdAndEidAndBuid(Long stageId, String eid, String buid);

    @Modifying
    @Transactional
    @Query("""
       UPDATE LeadStatusCustom l
        SET l.statusSequence = l.statusSequence-1 
               
               where l.enterpriseId =:eid
                      
               AND l.bussinessUnitId =:buid
               AND l.statusSequence> :deleteSequence    
       """)
    void resequenceAfterDelete(@Param("deleteSequence") Long deletedSequence,
                               @Param("eid") String eid,
                               @Param("buid") String buid);

    void deleteByIdAndEnterpriseIdAndBussinessUnitId(Long stageId, String eid, String buid);

    Optional<LeadStatusCustom>
    findTopByEnterpriseIdAndBussinessUnitIdAndStatusSequenceLessThanOrderByStatusSequenceDesc(
            String enterpriseId,
            String bussinessUnitId,
            Long statusSequence
    );

    Optional<LeadStatusCustom>
    findTopByEnterpriseIdAndBussinessUnitIdAndStatusSequenceGreaterThanOrderByStatusSequenceAsc(
            String enterpriseId,
            String bussinessUnitId,
            Long statusSequence
    );


    LeadStatusCustom findTopByEnterpriseIdAndBussinessUnitIdOrderByStatusSequenceAsc(String eid, String buid);
}

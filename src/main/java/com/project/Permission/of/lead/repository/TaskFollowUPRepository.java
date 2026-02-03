package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.TaskFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskFollowUPRepository extends JpaRepository<TaskFollowUp,Long> {
//    List<TaskFollowUp> findByBuidAndLid(String buid, String lid);

//    List<TaskFollowUp> findByBussinessUnitIdAndLeadId(String buid, String lid);

//    List<TaskFollowUp> findByBussinessUnitIdAndLeadIdOrderByCreatedAtDesc(String buid, String lid);

    List<TaskFollowUp> findByBussinessUnitIdAndLeadIdOrderByCreatedDateDesc(String buid, String lid);
}

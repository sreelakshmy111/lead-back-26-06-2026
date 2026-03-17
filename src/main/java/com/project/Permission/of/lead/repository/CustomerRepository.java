package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

    List<Customer> findByEidAndBuid(String enterpriseId, String bussinessUnitId);





//    Optional<Object> findBycustId(String cust_id);

//    Optional<Customer> findByCustId(String cust_id);

    Optional<Customer> findByCustId(String custId);

    boolean existsByEmail(String email);
}

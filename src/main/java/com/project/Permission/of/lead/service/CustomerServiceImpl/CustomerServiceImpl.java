package com.project.Permission.of.lead.service.CustomerServiceImpl;

import com.project.Permission.of.lead.dto.CustomerDto;
import com.project.Permission.of.lead.entity.BussinessUnit;
import com.project.Permission.of.lead.entity.Customer;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.CustomerMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.CustomerRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.service.CustomerService;
import jakarta.transaction.Transactional;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /// CREATE CUSTOMERR.......................................................

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto, String eid, String buid, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        String CustomerId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                                              new Object[]{"CUSTOMER_CONTACT"},
                                              String.class
        );

        Customer createdCus= CustomerMapper.maptoCustomer(customerDto,eid,buid,loggedInUser.getUser_id());

        createdCus.set_active(true);
        createdCus.setCustId(CustomerId);
        createdCus.setCreated_by(loggedInUser.getUser_id());
        customerRepository.save(createdCus);
        CustomerDto createdCustomer=CustomerMapper.maptoCustomerDto(createdCus);
        return createdCustomer;
    }


//    ....get all customers...............................................
    @Override
    public List<CustomerDto> getCustomers(Users loggedInUser, String eid, String buid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));
        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Enterprise not found");
        }

        List<Customer> customers=customerRepository.findByEidAndBuid(eid,buid);
        return customers.stream().map(c->CustomerMapper.maptoCustomerDto(c)).collect(Collectors.toList());
    }


    // update customer contact..........................................
    @Override
    public CustomerDto updateContact(CustomerDto customerDto, Users loggedUser, String eid, String buid, String cuid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to Enterprise ");
        }

        Customer customer=customerRepository.findByCustId(cuid).
                orElseThrow(()->new RuntimeException("Customer contact not found"));

        customer.setFirst_name(customerDto.getFirst_name());
        customer.setLast_name(customerDto.getLast_name());
        customer.setGender(customerDto.getGender());
        customer.setDate_of_birth(customerDto.getDate_of_birth());
        customer.setIsd_code(customerDto.getIsd_code());
        customer.setPhone(customerDto.getPhone());

        customer.setEmail(customerDto.getEmail());
        customer.setUpdated_at(LocalDateTime.now());
        customer.setUpdated_by(loggedUser.getUser_id());
        customer.set_active(customerDto.is_active());

        Customer updatedCustomer=customerRepository.save(customer);
        return CustomerMapper.maptoCustomerDto(updatedCustomer);

    }
}

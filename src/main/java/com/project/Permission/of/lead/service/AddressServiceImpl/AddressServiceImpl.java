package com.project.Permission.of.lead.service.AddressServiceImpl;

import com.project.Permission.of.lead.dto.AddressDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.AdressMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepo;


    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;


    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private TeritoryRepoitory teritoryRepoitory;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        Address address= AdressMapper.mapToAddress(addressDto);
        Address saved=addressRepo.save(address);
        return AdressMapper.mapToAddressDto(saved);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> addreses=addressRepo.findAll();
        return addreses.stream().map(address -> AdressMapper.mapToAddressDto(address)).collect(Collectors.toList());

    }


      @Override
    public String linkAddress(Map<String, Object> requestData) {
        String entity = (String) requestData.get("entity");
        Long addressId = Long.valueOf(requestData.get("addressId").toString());
        String entityId = requestData.get("entityId").toString();

        switch (entity) {

            case "Enterprise":
                Enterprise enterprise = enterpriseRepostory.
                        findByEid(entityId).orElseThrow(() -> new RuntimeException("Enterprise not found"));
                enterprise.setAddressId(addressId);
                enterpriseRepostory.save(enterprise);
                return "Address linked to enterprise Sucessfully";

            case "BussinessUnit":

                BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(entityId).
                        orElseThrow(() -> new RuntimeException("BussinessUnit not found"));

                bussinessUnit.setAddressId(addressId);
                bussinessUnitRepository.save(bussinessUnit);
                return "Address linked to Bu Sucessfully";

            case "PersonalManagement":
                PersonalManagement personalManagement=personalRepository.findByEmpId(entityId).
                        orElseThrow(()-> new RuntimeException("Personal Details not found"));

                personalManagement.setAddressId(addressId);
                personalRepository.save(personalManagement);

                return "Address linked to Employee Sucessfully";

            case "Customer":
                Customer customer = customerRepository.findByCustId(entityId)
                        .orElseThrow(() -> new RuntimeException("Customer not found"));

                customer.setAddress_id(addressId);
                customerRepository.save(customer);

                return "Address linked to Customer Sucessfully";


            default:System.out.println("invalid entity");
                return "No valid entity";


        }

    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        Address address=addressRepo.findById(addressId).
                orElseThrow(() -> new RuntimeException("Address not found"));

        address.setDoorNumber(addressDto.getDoorNumber());
        address.setBuildingName(addressDto.getBuildingName());

        address.setStreet(addressDto.getStreet());
        address.setLocality(addressDto.getLocality());
        address.setArea(addressDto.getArea());
        address.setCity(addressDto.getCity());

        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());

        Address updateAdress=addressRepo.save(address);
        return AdressMapper.mapToAddressDto(updateAdress);
    }


}


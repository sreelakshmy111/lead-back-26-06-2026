package com.project.Permission.of.lead.service.ServieceCatalougeServiceImpl;

import com.project.Permission.of.lead.dto.ServiceCatalogueDto;
import com.project.Permission.of.lead.entity.BussinessUnit;
import com.project.Permission.of.lead.entity.Catalogue.ServiceCatalogue;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.ServiceCatalogueMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.ServiceCatalogueRepository;
import com.project.Permission.of.lead.service.ServiceCatalogueService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceCatalogueImpl implements ServiceCatalogueService {

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private ServiceCatalogueRepository serviceCatalogueRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public ServiceCatalogueDto createServiceGroup(ServiceCatalogueDto serviceCatalogueDto1, String eid, String buid, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        String sid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[] {"SERVICE GROUP"},
                String.class
        );

        ServiceCatalogue serviceCatalouge= ServiceCatalogueMapper.mapToServiceCatalogue(serviceCatalogueDto1,eid,buid,loggedInUser.getUser_id());
        serviceCatalouge.setActive(true);
        serviceCatalouge.setClassification("SG");
        serviceCatalouge.setSid(sid);

        serviceCatalogueRepository.save(serviceCatalouge);
        return ServiceCatalogueMapper.mapToServiceCatalogueDto(serviceCatalouge);



    }

    //..............create service type......................................................................

    @Override
    public ServiceCatalogueDto createServiceType(ServiceCatalogueDto serviceCatalogueDto, String eid, String buid, String sgid, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }
        ServiceCatalogue serviceGroup=serviceCatalogueRepository.findBySid(sgid).
                orElseThrow(()-> new RuntimeException("Service Group not Found"));

            String ServiceTypeId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                                                new Object[] {"SERVICE TYPE"},
                                                String.class);

        ServiceCatalogue serviceType=ServiceCatalogueMapper.mapToServiceCatalogue(serviceCatalogueDto,eid,buid,loggedInUser.getUser_id());
        serviceType.setActive(true);
        serviceType.setClassification("ST");
        serviceType.setParentId(sgid);
        serviceType.setSid(ServiceTypeId);
        serviceCatalogueRepository.save(serviceType);
        return ServiceCatalogueMapper.mapToServiceCatalogueDto(serviceType);

    }

    // create service item.........................................................................................

    @Override
    public ServiceCatalogueDto createServiceItem(ServiceCatalogueDto serviceCatalogueDto, String eid, String buid, String serviceGroupId, String stid, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }
        ServiceCatalogue serviceGroup=serviceCatalogueRepository.findBySid(serviceGroupId).
                orElseThrow(()-> new RuntimeException("Service Group not Found"));

        ServiceCatalogue serviceType=serviceCatalogueRepository.findBySid(stid).
                orElseThrow(()->new RuntimeException("Service Type Not Found"));

        String ServiceItemId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                                                                new Object[] {"SERVICE ITEM"},
                                                                String.class);

        ServiceCatalogue item=ServiceCatalogueMapper.mapToServiceCatalogue(serviceCatalogueDto,eid,buid,loggedInUser.getUser_id());
        item.setActive(true);
        item.setClassification("SI");
        item.setParentId(stid);
        item.setSid(ServiceItemId);
        serviceCatalogueRepository.save(item);
        return ServiceCatalogueMapper.mapToServiceCatalogueDto(item);
    }

//.........find by service group......................................................................................

    @Override
    public List<ServiceCatalogueDto> getServiceGroups(String enterpriseId, String bussinessUnitId, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepostory.findByEid(enterpriseId).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(bussinessUnitId).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(enterpriseId)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        List<ServiceCatalogue> getGroups=serviceCatalogueRepository.findDistinctServiceGroup(enterpriseId,bussinessUnitId);
        List<ServiceCatalogueDto> serviceCatalogueGroups=getGroups.stream().map(g->ServiceCatalogueMapper.mapToServiceCatalogueDto(g)).collect(Collectors.toList());
        return serviceCatalogueGroups;
    }


//................Find by servicetypes............................................................................

    @Override
    public List<ServiceCatalogueDto> getServiceTypes( String eid, String buid, String sgid, Users loggedInUser) {
        Enterprise enterprise = enterpriseRepostory.findByEid(eid).
                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(buid).
                orElseThrow(() -> new RuntimeException("BussinessUnit Not Found"));


        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        List<ServiceCatalogue> serviceGroupList = serviceCatalogueRepository.findByParentId(sgid);
        if (serviceGroupList.isEmpty()) {
            throw new RuntimeException("Service Group Not Found");
        }

        List<ServiceCatalogue> types =
                serviceCatalogueRepository.findByEidAndBuidAndParentId(eid,buid,sgid);

        List<ServiceCatalogueDto> serviceTypes =
                types.stream()
                        .map(t -> ServiceCatalogueMapper.mapToServiceCatalogueDto(t))
                        .collect(Collectors.toList());

        return serviceTypes;

    }

    //.......get service items...............................................

    @Override
    public List<ServiceCatalogueDto> getServiceItems(String eid, String buid, String sgid, String stid, Users loggedInUser) {
        Enterprise enterprise = enterpriseRepostory.findByEid(eid).
                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(buid).
                orElseThrow(() -> new RuntimeException("BussinessUnit Not Found"));


        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        ServiceCatalogue serviceGroup =
                serviceCatalogueRepository.findBySid(sgid)
                        .orElseThrow(() -> new RuntimeException("Service Group Not Found"));


        ServiceCatalogue serviceType =
                serviceCatalogueRepository.findBySid(stid)
                        .orElseThrow(() -> new RuntimeException("Service Type Not Found"));

        if (!serviceType.getParentId().equals(sgid)) {
            throw new RuntimeException("Service Type does not belong to this Service Group");
        }



        List<ServiceCatalogue> servicesItems=serviceCatalogueRepository.findByEidAndBuidAndParentId(eid,buid,stid);

        return servicesItems.stream().map(s -> ServiceCatalogueMapper.mapToServiceCatalogueDto(s)).collect(Collectors.toList());

    }

    ///  get service catalogues.............................................................


    @Override
    public List<ServiceCatalogueDto> getFullCatalogue(String eid, String buid) {
        Enterprise enterprise = enterpriseRepostory.findByEid(eid).
                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(buid).
                orElseThrow(() -> new RuntimeException("BussinessUnit Not Found"));


        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }


        List<ServiceCatalogue> data=serviceCatalogueRepository.findByEidAndBuid(eid,buid);
        return data.stream().map(d->ServiceCatalogueMapper.mapToServiceCatalogueDto(d)).collect(Collectors.toList());

    }


    //.................get all servicetype...................................................................
//    @Override
//    public List<ServiceCatalogueDto> getServiceType(String enterpriseId, String bussinessUnitId, Users loggedInUser) {
//        Enterprise enterprise = enterpriseRepostory.findByEid(enterpriseId).
//                orElseThrow(() -> new RuntimeException("Enterprise Not Found"));
//
//        BussinessUnit bussinessUnit = bussinessUnitRepository.findByBuid(bussinessUnitId).
//                orElseThrow(() -> new RuntimeException("BussinessUnit Not Found"));
//
////        / Fetch all service types for this enterprise + BU
//        List<ServiceCatalogue> serviceTypes = serviceCatalogueRepository.findByEnterpriseIdAndBussinessUnitId(enterpriseId, bussinessUnitId);
//
//        // Convert to DTOs
//        List<ServiceCatalogueDto> typeDtos = serviceTypes.stream().map(t -> ServiceCatalogueMapper.mapToServiceCatalogueDto(t)).collect(Collectors.toList());
//        return typeDtos;
//
//
//    }



//    @Override
//    public ServiceCatalogueDto getServiceType(String enterpriseId, String bussinessUnitId, String serviceGroupId, Users loggedInUser) {
//
//
//
//    }

}

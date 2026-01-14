package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.ServiceCatalogueDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface ServiceCatalogueService {
    ServiceCatalogueDto createServiceGroup(ServiceCatalogueDto serviceCatalogueDto1, String eid, String buid, Users loggedInUser);

    ServiceCatalogueDto createServiceType(ServiceCatalogueDto serviceCatalogueDto, String eid, String buid, String sgid, Users loggedInUser);

    ServiceCatalogueDto createServiceItem(ServiceCatalogueDto serviceCatalogueDto, String eid, String buid, String sgid, String stid, Users loggedInUser);

    List<ServiceCatalogueDto> getServiceGroups(String eid, String buid, Users loggedInUser);

    List<ServiceCatalogueDto> getServiceTypes( String eid, String buid, String sgid, Users loggedInUser);

    List<ServiceCatalogueDto> getServiceItems(String eid, String buid, String sgid, String stid, Users loggedInUser);

    List<ServiceCatalogueDto> getFullCatalogue(String eid, String buid);

//    ServiceCatalogueDto getServiceType(String enterpriseId, String bussinessUnitId, String serviceGroupId, Users loggedInUser);

//    List<ServiceCatalogueDto> getServiceType(String enterpriseId, String bussinessUnitId, Users loggedInUser);
}

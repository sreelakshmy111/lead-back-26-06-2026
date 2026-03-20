package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.ServiceCatalogueDto;
import com.project.Permission.of.lead.entity.Catalogue.ServiceCatalogue;

import java.time.LocalDateTime;

public class ServiceCatalogueMapper {

    public static ServiceCatalogue mapToServiceCatalogue(ServiceCatalogueDto serviceCatalogueDto, String enterpriseId, String bussinessUnitId, String userId){
        LocalDateTime now=LocalDateTime.now();
        return new  ServiceCatalogue(
                serviceCatalogueDto.getId(),
              serviceCatalogueDto.getService(),
                serviceCatalogueDto.getClassification(),
                serviceCatalogueDto.getDescription(),
                serviceCatalogueDto.getSid(),
                serviceCatalogueDto.getParentId(),
                enterpriseId,
                bussinessUnitId,
                serviceCatalogueDto.isActive(),
                now,
                userId,
                null,
                serviceCatalogueDto.getUpdated_by()
        );
    }

    public static ServiceCatalogueDto mapToServiceCatalogueDto(ServiceCatalogue serviceCatalogue) {
        return new ServiceCatalogueDto(
                serviceCatalogue.getId(),
                serviceCatalogue.getService(),
                serviceCatalogue.getClassification(),
                serviceCatalogue.getDescription(),
                serviceCatalogue.getSid(),
                serviceCatalogue.getParentId(),
                serviceCatalogue.getEid(),
                serviceCatalogue.getBuid(),
                serviceCatalogue.isActive(),
                serviceCatalogue.getCreated_at(),
                serviceCatalogue.getCreated_by(),
                serviceCatalogue.getUpdated_at(),
                serviceCatalogue.getUpdated_by()!=null? serviceCatalogue.getUpdated_by():null
        );
    }

}

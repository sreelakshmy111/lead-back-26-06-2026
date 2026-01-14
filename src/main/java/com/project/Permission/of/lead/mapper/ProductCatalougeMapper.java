package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.ProductCatalogueDto;
import com.project.Permission.of.lead.entity.Catalogue.ProductCatalogue;

import java.time.LocalDateTime;

public class ProductCatalougeMapper {

    public static ProductCatalogue mapToProductCatalogue(ProductCatalogueDto productCatalogueDto, String enterpriseId, String bussinessUnitId, Long userId) {
        LocalDateTime  now=LocalDateTime.now();
        return new ProductCatalogue(
                productCatalogueDto.getId(),
                productCatalogueDto.getProduct(),
                productCatalogueDto.getClassification(),
                productCatalogueDto.getDescription(),
                productCatalogueDto.getPid(),
                productCatalogueDto.getParentId(),
                enterpriseId,
                bussinessUnitId,
                productCatalogueDto.isActive(),
                now,
                userId,
                null,
                productCatalogueDto.getUpdated_by()


        );
    }

    public static ProductCatalogueDto mapToProductCatalogueDto(ProductCatalogue productCatalogue) {
        return new ProductCatalogueDto(
                productCatalogue.getId(),
                productCatalogue.getProduct(),
                productCatalogue.getClassification(),
                productCatalogue.getDescription(),
                productCatalogue.getPid(),
                productCatalogue.getParentId(),
                productCatalogue.getEid(),
                productCatalogue.getBuid(),
                productCatalogue.isActive(),
                productCatalogue.getCreated_at(),
                productCatalogue.getCreated_by(),
                productCatalogue.getUpdated_at(),
                productCatalogue.getUpdated_by()!=null? productCatalogue.getUpdated_by():null
        );
    }

}

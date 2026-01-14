package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.ProductCatalogueDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface ProductCatalogueService {
    ProductCatalogueDto createProductGroup(ProductCatalogueDto productCatalogueDto, Users loggedInUser, String eid, String buid);

    ProductCatalogueDto createProductType(ProductCatalogueDto requestBody, Users loggedInUser, String eid, String buid, String pgi);

    ProductCatalogueDto createProductSku(ProductCatalogueDto productCatalogueDto, Users loggedInUser, String eid, String buid, String pgid, String ptid);

    List<ProductCatalogueDto> getProductTypes(String eid, String buid, String pgid);

    List<ProductCatalogueDto> getProductGroups(String eid, String buid);

    List<ProductCatalogueDto> getProductSku(String eid, String buid, String pgid, String ptid);

    List<ProductCatalogueDto> getFullCatalogue(String eid, String buid);
}

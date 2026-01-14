package com.project.Permission.of.lead.service.ProductCatalogue;

import com.project.Permission.of.lead.dto.ProductCatalogueDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.entity.Catalogue.ProductCatalogue;
import com.project.Permission.of.lead.mapper.ProductCatalougeMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.ProductCatalogueRepository;
import com.project.Permission.of.lead.service.ProductCatalogueService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductCatalogueImpl implements ProductCatalogueService {

    @Autowired
    EnterpriseRepostory enterpriseRepostory;

    @Autowired
    BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private ProductCatalogueRepository pcRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductCatalogueRepository productrepo;

    // ----create product group.....................................
    @Override
    public ProductCatalogueDto createProductGroup(ProductCatalogueDto productCatalogueDto, Users loggedInUser, String eid, String buid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()-> new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        String productId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                                                        new Object[] {"PRODUCT GROUP"},
                                                         String.class
                       );

        ProductCatalogue group= ProductCatalougeMapper.mapToProductCatalogue(productCatalogueDto,eid,buid,loggedInUser.getUser_id());
        group.setPid(productId);
        group.setClassification("PG");
        group.setActive(true);
        pcRepository.save(group);
        return ProductCatalougeMapper.mapToProductCatalogueDto(group);

    }

    //.......create product type....................................................................
    @Override
    public ProductCatalogueDto createProductType(ProductCatalogueDto productCatalogueDto, Users loggedInUser, String eid, String buid, String pgid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        ProductCatalogue productCatalogue=productrepo.findByPid(pgid).
                orElseThrow(()->new RuntimeException("Product Group not Found"));

        String productTypeId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                new Object[] {"PRODUCT TYPE"},
                String.class
        );

        System.out.println("Checking BU: " + buid);

        ProductCatalogue productType=ProductCatalougeMapper.mapToProductCatalogue(productCatalogueDto,eid,buid,loggedInUser.getUser_id());
        productType.setPid(productTypeId);
        productType.setParentId(pgid);
        productType.setClassification("PT");
        productType.setActive(true);
        pcRepository.save(productType);
        return ProductCatalougeMapper.mapToProductCatalogueDto(productType);

    }

    //...............create product sku........................................................

    @Override
    public ProductCatalogueDto createProductSku(ProductCatalogueDto productCatalogueDto, Users loggedInUser, String enterpriseId, String bussinessUnitId, String productGroupId, String productTypeId) {
        Enterprise enterprise=enterpriseRepostory.findByEid(enterpriseId).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(bussinessUnitId).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(enterpriseId)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }

        ProductCatalogue productCatalogue=productrepo.findByPid(productGroupId).
                orElseThrow(()->new RuntimeException("Product Group not Found"));

        ProductCatalogue productType=productrepo.findByPid(productTypeId).
                orElseThrow(()->new RuntimeException("Product Type not Found"));

        String productSkuId=jdbcTemplate.queryForObject("SELECT create_entity_id(?)",
                new Object[] {"PRODUCT SKU"},
                String.class
        );

        System.out.println("Checking BU: " + bussinessUnitId);

        ProductCatalogue productSku=ProductCatalougeMapper.mapToProductCatalogue(productCatalogueDto,enterpriseId,bussinessUnitId,loggedInUser.getUser_id());
        productSku.setPid(productSkuId);
        productSku.setParentId(productTypeId);
        productSku.setClassification("PS");
        productSku.setActive(true);
        pcRepository.save(productSku);
        return ProductCatalougeMapper.mapToProductCatalogueDto(productSku);
    }


    @Override
    public List<ProductCatalogueDto> getProductGroups( String eid, String buid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }


        // Fetch ProductCatalogue objects for groups
        List<ProductCatalogue> productGroups = productrepo.findDistinctProductGroups(eid, buid);

        // Map to DTOs
        List<ProductCatalogueDto> groupdto=productGroups.stream().map(g->ProductCatalougeMapper.mapToProductCatalogueDto(g)).collect(Collectors.toList());
        return groupdto;


    }



    /// ////............get product types...............................................................
    @Override
    public List<ProductCatalogueDto> getProductTypes(String eid, String buid, String pgid) {


        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }
        System.out.println("Checking parentId: " + pgid);
        List<ProductCatalogue> productGroupList = productrepo.findByParentId(pgid);
        if (productGroupList.isEmpty()) {
            throw new RuntimeException("Product Group Not Found");
        }

        List<ProductCatalogue> types =
                productrepo.findByEidAndBuidAndParentId(eid,buid,pgid);

        List<ProductCatalogueDto> productTypes =
                types.stream()
                        .map(t -> ProductCatalougeMapper.mapToProductCatalogueDto(t))
                        .collect(Collectors.toList());

        return productTypes;


    }
    //............get product sku..................................................................

    @Override
    public List<ProductCatalogueDto> getProductSku(String eid, String buid, String pgid, String ptid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }



        ProductCatalogue productGroup =productrepo.findByPid(pgid).
                orElseThrow(()->new RuntimeException("Product Group Not Found"));

        ProductCatalogue productType=productrepo.findByPid(ptid).
                orElseThrow(()->new RuntimeException("Product Type Not Found"));

        if(!productType.getParentId().equals(pgid)){
            throw new RuntimeException("Product type not belong to the product group");
        }

        List<ProductCatalogue> productSku=productrepo.findByEidAndBuidAndParentId(eid,buid,ptid);

        return productSku.stream().map(g->ProductCatalougeMapper.mapToProductCatalogueDto(g)).collect(Collectors.toList());

    }
/// GET FULL CATALOGUE..............................................................
    @Override
    public List<ProductCatalogueDto> getFullCatalogue(String eid, String buid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise Not Found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.
                findByBuid(buid).orElseThrow(()->new RuntimeException("BussinessUnit Not Found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("Bussiness unit not belong to the enterprise ");
        }


        List<ProductCatalogue> all=pcRepository.findByEidAndBuid(eid,buid);

        return all.stream().map(l-> ProductCatalougeMapper.mapToProductCatalogueDto(l)).collect(Collectors.toList());


    }
}

package com.b2bAr.arzoo.service;


import com.b2bAr.arzoo.entity.*;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.mapper.ProductVariationMapper;
import com.b2bAr.arzoo.repository.ProductAttributeRepository;
import com.b2bAr.arzoo.repository.ProductRepository;
import com.b2bAr.arzoo.repository.ProductVariationRepository;
import com.b2bAr.arzoo.request.ProductVariationRequest;
import com.b2bAr.arzoo.request.ProductVariationUpdate;
import com.b2bAr.arzoo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductVariationService {


    @Autowired
    public final ProductVariationRepository productVariationRepository;

    @Autowired
    public final ProductVariationMapper productVariationMapper;

    @Autowired
    public final ProductRepository productRepository;
    @Autowired
    public final ProductMapper productMapper;

    @Autowired
    public final ProductAttributeRepository productAttributeRepository;


    public ProductVariationService(ProductVariationRepository productVariationRepository, ProductVariationMapper productVariationMapper, ProductRepository productRepository, ProductMapper productMapper, ProductAttributeRepository productAttributeRepository) {
        this.productVariationRepository = productVariationRepository;
        this.productVariationMapper = productVariationMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productAttributeRepository = productAttributeRepository;
    }
    public ResponseEntity<?> saveVariation(ProductVariationRequest productVariationRequest, int productId, int attributeId) {
        try {
            Optional<ProductsEntity> entity = productRepository.findById(productId);

            if (entity.isPresent()) {
                ProductsEntity entity1 = entity.get();
                Optional<ProductAttribute> entity2 = productAttributeRepository.findById(attributeId);
                if (entity2.isPresent()) {
                    ProductAttribute attribute = entity2.get();
                    List<String> values = productVariationRequest.getValue();
                    List<ProductVariations> variations = new ArrayList<>();
                    for (String value : values) {
                        ProductVariations variation = new ProductVariations();
                        variation.setProductsEntity(entity1);
                        variation.setProductAttribute(attribute);
                        variation.setValue(Collections.singletonList(value));
                        variations.add(variation);
                    }
                    productVariationRepository.saveAll(variations);
                    log.info("Variations saved successfully");
                    ResultResponse response = new ResultResponse();
                    response.setResult("Variations saved successfully");
                    return ResponseEntity.ok(response);
                } else {
                    log.error("There is no attribute with ID: " + attributeId);
                    ResultResponse response = new ResultResponse();
                    response.setResult("There is no attribute with ID: " + attributeId);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                log.error("There is no product with ID: " + productId);
                ResultResponse response = new ResultResponse();
                response.setResult("There is no product with ID: " + productId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Error saving variations: " + e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult("Error saving variations: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


/*    public ResponseEntity<?> getVariation(){
        List<ProductVariations> variations = productVariationRepository.findAll();
        try {
            if (!variations.isEmpty()) {
                List<ProductVariationResponse2> variationResponse2s = new ArrayList<>();
                for (ProductVariations variations1 : variations) {
                    ProductVariationResponse2 variationResponse2 = productVariationMapper.entityToResponse2(variations1);
                    ProductsEntity entities = variations1.getProductsEntity();
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(entities);
                    String url = entities.getImage_url();
                    if (!url.isEmpty()) {
                            byte[] fileContent = Files.readAllBytes(Paths.get(url));
                            String base = Base64.getEncoder().encodeToString(fileContent);
                            productResponse2.setImage_url(base);

                        variationResponse2.setProductsEntity(productResponse2);
                    }
                    variationResponse2s.add(variationResponse2);
                }

                return ResponseEntity.ok(variationResponse2s);

            } else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.ok(response);

        }
    }*/

    public ResponseEntity<?> getVariation() {
        try {
            List<ProductVariations> variations = productVariationRepository.findAll();
            List<ProductVariationResponse2> variationResponses2 = variations.stream()
                    .map(productVariationMapper::entityToResponse2)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(variationResponses2);
        }  catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    public ResponseEntity<?> getVariationById(int variationId) {
        try {
            Optional<ProductVariations> entity = productVariationRepository.findById(variationId);
            if (entity.isPresent()) {
                ProductVariations variations = entity.get();

                ProductsEntity productsEntity = entity.get().getProductsEntity();
                ProductResponse2 productResponse = productMapper.entityToResponse3(productsEntity);
                ProductVariationResponse2 productVariationResponse = productVariationMapper.entityToResponse2(variations);
                String url = productsEntity.getImage_url();
                if (!url.isEmpty() && url!=null){
                    File file = new File(url);
                    byte[] filecontact = Files.readAllBytes(Paths.get(url));
                    String base = Base64.getEncoder().encodeToString(filecontact);
                    productResponse.setImage_url(base);

                }
                productVariationResponse.setProductsEntity(productResponse);

                return ResponseEntity.ok(productVariationResponse);

            } else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty Records");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> deleteById(int variationId) {
        Optional<ProductVariations> entity = productVariationRepository.findById(variationId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                productVariationRepository.deleteById(variationId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id "+variationId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int variationId, ProductVariationUpdate productVariationUpdate) {
        try {
            Optional<ProductVariations> entity = productVariationRepository.findById(variationId);
            if (entity.isPresent()) {
                ProductVariations existingDetails = entity.get();
                ProductVariations updateEntity = productVariationMapper.updateRequest(existingDetails, productVariationUpdate);

                productVariationRepository.save(updateEntity);
                log.info("Updated details saved successfully " + variationId);
                ResultResponse response = new ResultResponse();
                response.setResult("Updated details saved successfully " + variationId);
                return ResponseEntity.ok(response);
            }else {
                log.error("Please check Id "+variationId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please Check id "+variationId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}



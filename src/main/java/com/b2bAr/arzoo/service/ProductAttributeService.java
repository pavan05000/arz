package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.ProductAttribute;
import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductVariations;
import com.b2bAr.arzoo.mapper.ProductAttributeMapper;
import com.b2bAr.arzoo.repository.ProductAttributeRepository;
import com.b2bAr.arzoo.request.*;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.response.ProductAttributeResponse;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductAttributeService {

    @Autowired
    public final ProductAttributeRepository productAttributeRepository;
    @Autowired
    public final ProductAttributeMapper productAttributeMapper;


    public ProductAttributeService(ProductAttributeRepository productAttributeRepository, ProductAttributeMapper productAttributeMapper) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeMapper = productAttributeMapper;
    }


    public ResponseEntity<?> saveAttribute(ProductAttributeRequest productAttributeRequest) {
        try {

            ProductAttribute categories = productAttributeMapper.requestToEntity(productAttributeRequest);

            productAttributeRepository.save(categories);
            log.info("Saved Successfully");
            ResultResponse response = new ResultResponse();
            response.setResult("Completed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<?> getAttributes() {
        List<ProductAttribute> attributes = productAttributeRepository.findAll();
        try
        {
            if (!attributes.isEmpty()) {
                return ResponseEntity.ok(productAttributeMapper.entityToResponse(attributes));

            } else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("no attributes");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return an empty list if there are no categories
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    public ResponseEntity<?> getDetailsById(int attributeId) {
        try {
            Optional<ProductAttribute> entity = productAttributeRepository.findById(attributeId);
            if (entity.isPresent()) {
                ProductAttributeResponse productAttributeResponse =   productAttributeMapper.entityToResponse2(entity.get());
                return ResponseEntity.ok(productAttributeResponse);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    public ResponseEntity<?> deleteById(int attributeId) {
        Optional<ProductAttribute> entity = productAttributeRepository.findById(attributeId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                productAttributeRepository.deleteById(attributeId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Please check id "+attributeId );
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id "+attributeId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int attributeId, ProductAttributeUpdate attributeUpdate) {
        try {
            Optional<ProductAttribute> entity = productAttributeRepository.findById(attributeId);
            if (entity.isPresent()) {
                ProductAttribute existingDetails = entity.get();
                ProductAttribute updateEntity = productAttributeMapper.updateRequest(existingDetails, attributeUpdate);

                productAttributeRepository.save(updateEntity);
                log.info("Updated Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Updated successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Details are not Updated, Check ID "+attributeId);
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not Updated, Id is not founded ");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

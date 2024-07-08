package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.mapper.CategoryMapper;
import com.b2bAr.arzoo.repository.CategoryRepository;
import com.b2bAr.arzoo.request.CategoryRequest;
import com.b2bAr.arzoo.request.CategoryUpdateRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    public final CategoryRepository categoryRepository;

    @Autowired
    public final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ResponseEntity<?> saveCategory(CategoryRequest categoryRequest) {
        try {
            Optional<ProductCategories> productCategories = categoryRepository.findCategoryName(categoryRequest.getCategoryName());
            if (productCategories.isEmpty()) {
                ProductCategories categories = categoryMapper.requestToEntity(categoryRequest);
                categoryRepository.save(categories);
                log.info("Saved Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Saved Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("The Category is already Exist");
                ResultResponse response = new ResultResponse();
                response.setResult("The Category is already Exist");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> getCategories() {
        List<ProductCategories> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        try {
            if (!categories.isEmpty()) {
                for (ProductCategories category : categories) {
                    CategoryResponse categoryResponse = categoryMapper.entityToResponse2(category);
                    categoryResponses.add(categoryResponse);
                }
                return ResponseEntity.ok(categoryResponses);
            } else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty Records");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);// Return an empty list if there are no categories
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return an empty list in case of exception
        }
    }


    public ResponseEntity<?> getDetailsById(int categoryId) {
        try {
            Optional<ProductCategories> entity = categoryRepository.findById(categoryId);
            if (entity.isPresent()) {
                CategoryResponse categoryRespons = categoryMapper.entityToResponse2(entity.get());
                return ResponseEntity.ok(categoryRespons);
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
    public ResponseEntity<?> deleteById(int categoryId) {
        Optional<ProductCategories> entity = categoryRepository.findById(categoryId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                categoryRepository.deleteById(categoryId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Check Id "+categoryId);
                ResultResponse response = new ResultResponse();
                response.setResult("check id "+categoryId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int categoryId, CategoryUpdateRequest categoryRequest) {
        try {
            Optional<ProductCategories> entity = categoryRepository.findById(categoryId);
            if (entity.isPresent()) {
                ProductCategories existingDetails = entity.get();
                ProductCategories updateEntity = categoryMapper.updateRequest(existingDetails, categoryRequest);

                categoryRepository.save(updateEntity);
                log.info("Updated Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Updated successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Details are not Updated");
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not updated");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

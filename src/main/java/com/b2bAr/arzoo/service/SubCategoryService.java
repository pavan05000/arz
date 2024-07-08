package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.entity.SubCategoryEntity;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.mapper.SubCategoryMapper;
import com.b2bAr.arzoo.repository.CategoryRepository;
import com.b2bAr.arzoo.repository.SubCategoryRepository;
import com.b2bAr.arzoo.request.SubCategoryUpdateRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.response.ProductResponse2;
import com.b2bAr.arzoo.response.ResultResponse;
import com.b2bAr.arzoo.response.SubCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubCategoryService {


    public final SubCategoryRepository subCategoryRepository;

    public final ProductMapper productMapper;

    public  final CategoryRepository categoryRepository;
    public final SubCategoryMapper subCategoryMapper;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository, ProductMapper productMapper, CategoryRepository categoryRepository, SubCategoryMapper subCategoryMapper) {
        this.subCategoryRepository = subCategoryRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.subCategoryMapper = subCategoryMapper;
    }

    public ResponseEntity<?> saveSubCategory(SubCategoryUpdateRequest subCategoryUpdateRequest, int categoryId) {
        try {
            Optional<ProductCategories> entity = categoryRepository.findById(categoryId);

            if(entity.isPresent()) {
                ProductCategories entity1 = entity.get();


                SubCategoryEntity categories = subCategoryMapper.requestToEntity(subCategoryUpdateRequest);
                ProductsEntity productsEntity = new ProductsEntity();
                categories.setProductCategories(entity1);
//                    categories.setProductsEntity(productsEntity);
                productsEntity.setSubCategoryEntity(categories);
                subCategoryRepository.save(categories);

                log.info("saved Successfully ");
                ResultResponse response = new ResultResponse();
                response.setResult("saved Successfully ");
                return ResponseEntity.ok(response);

            }else {
                log.error("Check CategoryId");
                ResultResponse response = new ResultResponse();
                response.setResult("Check CategoryId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



    public ResponseEntity<?> getSubCategories() {
        List<SubCategoryEntity> categories = subCategoryRepository.findAll();
        if (!categories.isEmpty()) {
            List<SubCategoryResponse> subCategoryResponses = new ArrayList<>();
            for (SubCategoryEntity categoryEntity : categories) {
                SubCategoryResponse subCategoryResponse = subCategoryMapper.entityToResponse2(categoryEntity);
                List<ProductResponse2> productResponses = new ArrayList<>(); // List to hold product responses
                List<ProductsEntity> entities = categoryEntity.getProductsEntity();
                for (ProductsEntity productsEntity : entities) {
                    ProductResponse2 productResponse2 = productMapper.entityToResponse3(productsEntity);
                    String url = productsEntity.getImage_url();
                    if (url != null && !url.isEmpty()) {
                        try {
                            byte[] bytes = Files.readAllBytes(Paths.get(url));
                            String base = Base64.getEncoder().encodeToString(bytes);
                            productResponse2.setImage_url(base);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ResultResponse response = new ResultResponse();
                            response.setResult(e.getMessage());
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return response in case of exception
                        }
                    }
                    productResponses.add(productResponse2); // Add product response to list
                }
                subCategoryResponse.setProductsEntity(productResponses); // Set list of product responses
                subCategoryResponses.add(subCategoryResponse);
            }
            return ResponseEntity.ok(subCategoryResponses);
        } else {
            log.error("Empty Records");
            ResultResponse response = new ResultResponse();
            response.setResult("Empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    public ResponseEntity<?> getCategoriesById(int subCategoryId) {
        try {
            Optional<SubCategoryEntity> subEntity = subCategoryRepository.findById(subCategoryId);
            if (subEntity.isPresent()) {
                SubCategoryResponse categoryResponse = subCategoryMapper.entityToResponse2(subEntity.get());
                return ResponseEntity.ok(categoryResponse);
            } else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    public ResponseEntity<?> deleteById(int subCategoryId) {
        Optional<SubCategoryEntity> entity = subCategoryRepository.findById(subCategoryId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                subCategoryRepository.deleteById(subCategoryId);
                log.info("Deleted");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted");
                return ResponseEntity.ok(response);
            }else {
                log.info("Please check id "+subCategoryId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id "+subCategoryId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int subCategoryId, SubCategoryUpdateRequest categoryRequest) {
        try {
            Optional<SubCategoryEntity> entity = subCategoryRepository.findById(subCategoryId);
            if (entity.isPresent()) {
                SubCategoryEntity existingDetails = entity.get();
                SubCategoryEntity updateEntity = subCategoryMapper.updateRequest(existingDetails, categoryRequest);

                subCategoryRepository.save(updateEntity);
                log.info("Updated details saved successfully" + subCategoryId);
                ResultResponse response = new ResultResponse();
                response.setResult("Updated details saved successfully" + subCategoryId);
                return ResponseEntity.ok(response);
            }else {
                log.error("Details are not Updated, please Check Id "+subCategoryId);
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not Updated, please Check Id");
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



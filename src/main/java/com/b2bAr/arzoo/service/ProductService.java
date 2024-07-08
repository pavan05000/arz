package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductsEntity;
import com.b2bAr.arzoo.entity.SubCategoryEntity;
import com.b2bAr.arzoo.mapper.ProductMapper;
import com.b2bAr.arzoo.repository.CategoryRepository;
import com.b2bAr.arzoo.repository.ProductRepository;
import com.b2bAr.arzoo.repository.SubCategoryRepository;

import com.b2bAr.arzoo.request.ProductRequest;
import com.b2bAr.arzoo.request.ProductUpdateRequest;
import com.b2bAr.arzoo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class ProductService {

    @Autowired
    public final ProductRepository productRepository;

    @Autowired
    public final ProductMapper productMapper;
    @Autowired
    public final CategoryRepository categoryRepository;
    @Autowired
    public final SubCategoryRepository subCategoryRepository;

    @Value("${file.upload.path}")
    private String folderPath;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public ResponseEntity<?> saveProduct(ProductRequest productRequest, int categoryId, int subCategoryId, MultipartFile multipartFile) {
        try {
            Optional<ProductCategories> categories = categoryRepository.findById(categoryId);
            if(categories.isPresent()) {

                Optional<SubCategoryEntity> subEntity = subCategoryRepository.findById(subCategoryId);
                if (subEntity.isPresent()) {
                    ProductCategories categories1 = categories.get();
                    SubCategoryEntity subEntity1 = subEntity.get();
                    ProductsEntity entity = productMapper.requestToEntity(productRequest);
                    if (!multipartFile.isEmpty()) {
                        entity.setProductCategories(categories1);
                        entity.setSubCategoryEntity(subEntity1);
                        String filename = folderPath+multipartFile.getOriginalFilename();
                        multipartFile.transferTo(new File(filename));
                        entity.setImage_url(filename);
                    }
                    if (productRequest.getStock() >0) {
                        entity.setAvailability_status(true);
                        productRepository.save(entity);
                        ResultProductResponse response = new ResultProductResponse();
                        response.setProductId(entity.getProductId());
                        response.setResult("Saved Successfully");

                        log.info("Saved Successfully");
                        return ResponseEntity.ok(response);
                    }else{
                        ResultResponse response = new ResultResponse();
                        response.setResult("The Product is not Available");
                        log.info("The Product is not Available");
                        return ResponseEntity.ok(response);
                    }
                }else{
                    log.info("subCategory is not exist");
                    ResultResponse response = new ResultResponse();
                    response.setResult("subCategory is not exist");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }else {
                log.info("the category is not exist ");
                ResultResponse response = new ResultResponse();
                response.setResult("the category is not exist ");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("The error message for the product upload is :"+e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> getProduct(){
        List<ProductsEntity> prodocts = productRepository.findAll();
        if (!prodocts.isEmpty()) {
            List<ProductResponse> productResponses = new ArrayList<>();
            for (ProductsEntity entity :prodocts) {
                if (entity.getStock() <=0) {
                    entity.setAvailability_status(false);
                    productRepository.save(entity);
                }
                ProductResponse productResponse = productMapper.entityToResponse2(entity);
                String image_url = productResponse.getImage_url();

                if (!image_url.isEmpty() && image_url !=null) {
                    try {
                        File file =  new File(image_url);
                        // byte[] fileContent2 = Files.readAllBytes(file.toPath());
                        byte[] fileContent = Files.readAllBytes(Paths.get(image_url));
                        String base = Base64.getEncoder().encodeToString(fileContent);
                        productResponse.setImage_url(base);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ResultResponse response = new ResultResponse();
                        response.setResult(e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

                    }
                } productResponses.add(productResponse);

            }
            return ResponseEntity.ok(productResponses);
        }else {
            log.error("Empty Records");
            ResultResponse response = new ResultResponse();
            response.setResult("Empty Records");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    public ResponseEntity<?> getDetailsById(int productId) {

        Optional<ProductsEntity> entity = productRepository.findById(productId);
        if (entity.isPresent()) {
            ProductsEntity entity1 = entity.get();
            if (entity1.getStock()<=0){
                entity1.setAvailability_status(false);
            }
            ProductResponse productResponse = productMapper.entityToResponse2(entity1);

            String url = entity1.getImage_url();
            if (!url.isEmpty() && url != null) {
                try {
                    File file = new File(url);
                    byte[] fileContent = Files.readAllBytes(Paths.get(url));
                    String base = Base64.getEncoder().encodeToString(fileContent);
                    productResponse.setImage_url(base);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                    ResultResponse response = new ResultResponse();
                    response.setResult(e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }


            }
            return ResponseEntity.ok(productResponse);

        }else {
            log.error("Empty Records");
            ResultResponse response = new ResultResponse();
            response.setResult("Empty Records");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }


    }
    public ResponseEntity<?> deleteById(int productId) {
        Optional<ProductsEntity> entity = productRepository.findById(productId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                productRepository.deleteById(productId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Please check id "+productId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please check id "+productId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editProductDetails(int productId, ProductUpdateRequest updateRequest, MultipartFile newFile) {
        try {
            Optional<ProductsEntity> entity = productRepository.findById(productId);
            if (entity.isPresent()) {
                ProductsEntity existingDetails = entity.get();
                if (updateRequest.getStock()>0){
                    updateRequest.setAvailability_status(true);
                }
                ProductsEntity updateEntity = productMapper.updateRequest(existingDetails, updateRequest);

                if (newFile != null && !newFile.isEmpty()) {
                    // Process the new image file
                    String filename = folderPath + newFile.getOriginalFilename();
                    newFile.transferTo(new File(filename));
                    updateEntity.setImage_url(filename);
                }

                productRepository.save(updateEntity);
                log.info("Updated successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Updated successfully");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                log.error("Please Check ID "+productId);
                ResultResponse response = new ResultResponse();
                response.setResult("Please Check ID "+productId);
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


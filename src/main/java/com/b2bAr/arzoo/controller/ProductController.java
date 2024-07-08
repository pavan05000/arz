package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.CategoryUpdateRequest;
import com.b2bAr.arzoo.request.ProductRequest;
import com.b2bAr.arzoo.request.ProductUpdateRequest;
import com.b2bAr.arzoo.request.SubCategoryUpdateRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/addProduct/{categoryId}/{subCategoryId}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@ModelAttribute ProductRequest productRequest, @PathVariable int categoryId, @PathVariable int subCategoryId, @RequestPart("file") MultipartFile file){
        return productService.saveProduct(productRequest, categoryId, subCategoryId, file);
    }
    @GetMapping("/getProducts")
    public Object getProduct(){
        return productService.getProduct();

    }

    @GetMapping("/getProducts/{productId}")
    public ResponseEntity<?> getDetailsByIds(@PathVariable int productId){
        return productService.getDetailsById(productId);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteDetailsById(@PathVariable int productId){
        return productService.deleteById(productId);
    }

    @PutMapping("/productUpdate/{productId}")
    public ResponseEntity<?> updateDetails(@ModelAttribute ProductUpdateRequest productUpdateRequest, @PathVariable int productId,@RequestPart MultipartFile file){
        return productService.editProductDetails(productId, productUpdateRequest, file);
    }
}


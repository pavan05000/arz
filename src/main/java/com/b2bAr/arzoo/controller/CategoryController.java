package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.CategoryRequest;
import com.b2bAr.arzoo.request.CategoryUpdateRequest;
import com.b2bAr.arzoo.request.UserRequest;
import com.b2bAr.arzoo.request.UserUpdateRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.response.UserResponse;
import com.b2bAr.arzoo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
     public CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }

    @PostMapping("/post/category")
    public ResponseEntity<?> register(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/getCategories/{categoryId}")
    public ResponseEntity<?> getDetailsById(@PathVariable int categoryId) {
        return categoryService.getDetailsById(categoryId);
    }

    @GetMapping("/getCategories")
    public ResponseEntity<?> getAll() {
        return categoryService.getCategories();
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteDtailsById(@PathVariable int categoryId) {
        return categoryService.deleteById(categoryId);
    }

    @PutMapping("/categoriesUpdate/{categoryId}")
    public ResponseEntity<?> updateDetails(@RequestBody CategoryUpdateRequest categoryUpdateRequest, @PathVariable int categoryId){
        return categoryService.editDetails(categoryId, categoryUpdateRequest);
    }
}

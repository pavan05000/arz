package com.b2bAr.arzoo.controller;



import com.b2bAr.arzoo.request.SubCategoryUpdateRequest;
import com.b2bAr.arzoo.response.SubCategoryResponse;
import com.b2bAr.arzoo.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SubCategoryController {

    @Autowired
    public SubCategoryService subCategoryService;


    SubCategoryController(SubCategoryService subCategoryService){
        this.subCategoryService= subCategoryService;
    }

    @PostMapping("/subCategory/register/{categoryId}")
    public ResponseEntity<?> register( @RequestBody SubCategoryUpdateRequest subCategoryUpdateRequest, @PathVariable int categoryId){
        return subCategoryService.saveSubCategory(subCategoryUpdateRequest, categoryId);
    }
    @GetMapping("/getSubCategory/{subCategoryId}")
    public ResponseEntity<?> getDtailsById(@PathVariable int subCategoryId){
        return subCategoryService.getCategoriesById(subCategoryId);
    }
    @GetMapping("/getSubCategories")
    public ResponseEntity<?> getAll(){
        return subCategoryService.getSubCategories();
    }

    @DeleteMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<?> deleteDtailsById(@PathVariable int subCategoryId){
        return subCategoryService.deleteById(subCategoryId);
    }

    @PutMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<?> updateDetails(@RequestBody SubCategoryUpdateRequest categoryUpdateRequest, @PathVariable int subCategoryId){
        return subCategoryService.editDetails(subCategoryId, categoryUpdateRequest);
    }
}



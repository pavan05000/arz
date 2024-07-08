package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.ProductAttributeRequest;
import com.b2bAr.arzoo.request.ProductAttributeUpdate;
import com.b2bAr.arzoo.response.ProductAttributeResponse;
import com.b2bAr.arzoo.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductAttributeController {

    @Autowired
    public ProductAttributeService productAttributeService;

    ProductAttributeController(ProductAttributeService productAttributeService){
        this.productAttributeService = productAttributeService;
    }


    @PostMapping("/saveAttribute")
    public ResponseEntity<?> saveAttribute(@RequestBody ProductAttributeRequest attributeRequest){
        return productAttributeService.saveAttribute(attributeRequest);
    }

    @GetMapping("/getAttributes/{attributeId}")
    public ResponseEntity<?> getDetailsById(@PathVariable int attributeId){
        return productAttributeService.getDetailsById(attributeId);
    }
    @GetMapping("/getAttributes")
    public ResponseEntity<?> getAll(){
        return productAttributeService.getAttributes();
    }

    @DeleteMapping("/attributes/{attributeId}")
    public ResponseEntity<?> deleteDetailsById(@PathVariable int attributeId){
        return productAttributeService.deleteById(attributeId);
    }

    @PutMapping("/attributeUpdate/{attributeId}")
    public ResponseEntity<?> updateDetails(@RequestBody ProductAttributeUpdate productAttributeUpdate, @PathVariable int attributeId){
        return productAttributeService.editDetails(attributeId, productAttributeUpdate);
    }

}

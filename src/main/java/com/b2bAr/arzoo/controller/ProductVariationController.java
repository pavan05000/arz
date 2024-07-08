package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.ProductUpdateRequest;
import com.b2bAr.arzoo.request.ProductVariationRequest;
import com.b2bAr.arzoo.request.ProductVariationUpdate;
import com.b2bAr.arzoo.response.ProductVariationResponse2;
import com.b2bAr.arzoo.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductVariationController {


    @Autowired
    public final ProductVariationService productVariationService;


    public ProductVariationController(ProductVariationService productVariationService) {
        this.productVariationService = productVariationService;
    }

    @PostMapping("/variation/{productId}/{attributeId}")
    public ResponseEntity<?> saveVariation(@RequestBody ProductVariationRequest productVariationRequest, @PathVariable int productId,@PathVariable int attributeId){
        return productVariationService.saveVariation(productVariationRequest, productId, attributeId );

    }

    @GetMapping("/getVariations")
    public ResponseEntity<?> getAllDetails(){
        return productVariationService.getVariation();
    }

    @GetMapping("/getVariations/{variationId}")
    public ResponseEntity<?> getDetailsById(@PathVariable int variationId){
        return productVariationService.getVariationById(variationId);
    }

    @DeleteMapping("/variation/{variationId}")
    public ResponseEntity<?> deleteDetailsById(@PathVariable int variationId){
        return productVariationService.deleteById(variationId);
    }

    @PutMapping("/variationUpdate/{variationId}")
    public ResponseEntity<?> updateDetails( @PathVariable int variationId, @RequestBody ProductVariationUpdate productVariationUpdate){
        return productVariationService.editDetails(variationId, productVariationUpdate);
    }
}

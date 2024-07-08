package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.CategoryUpdateRequest;
import com.b2bAr.arzoo.request.DealsRequest;
import com.b2bAr.arzoo.response.CategoryResponse;
import com.b2bAr.arzoo.response.DealsResponse;
import com.b2bAr.arzoo.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DealsController {

    @Autowired
    public  DealsService dealsService;

    public DealsController(DealsService dealsService) {
        this.dealsService = dealsService;
    }


    @PostMapping("/deals")
    public ResponseEntity<?> register(@RequestBody DealsRequest dealsRequest){
        return dealsService.saveDeals(dealsRequest);
    }

    @GetMapping("/getDeals/{dealId}")
    public ResponseEntity<?> getDealsById(@PathVariable int dealId) {

        return dealsService.getDetailsById(dealId);
    }

    @GetMapping("/getAllDeals")
    public ResponseEntity<?> getAll() {
        return dealsService.getDeals();
    }

    @DeleteMapping("/deals/{dealId}")
    public ResponseEntity<?> deleteDetailsById(@PathVariable int dealId) {
        return dealsService.deleteById(dealId);
    }

    @PutMapping("/dealsUpdate/{dealId}")
    public ResponseEntity<?> updateDetails(@RequestBody DealsRequest dealsRequest, @PathVariable int dealId){
        return dealsService.editDetails(dealId, dealsRequest);
    }
}

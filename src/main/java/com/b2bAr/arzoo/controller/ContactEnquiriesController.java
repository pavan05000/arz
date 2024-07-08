package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.ContactEnquiriesRequest;
import com.b2bAr.arzoo.request.ContactEnquiriesRequest2;
import com.b2bAr.arzoo.response.ContactEnquiriesResponse;
import com.b2bAr.arzoo.service.ContactEnquiriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ContactEnquiriesController {


    public final ContactEnquiriesService contestEnquiriesService;

    @Autowired
    public ContactEnquiriesController(ContactEnquiriesService contestEnquiriesService) {
        this.contestEnquiriesService = contestEnquiriesService;
    }

    @PostMapping("/enquiry/{userId}")
    public ResponseEntity<?> post(@RequestBody ContactEnquiriesRequest contactEnquiriesRequest, @PathVariable int userId){
        return contestEnquiriesService.post(contactEnquiriesRequest, userId);
    }

    @PostMapping("/response/{enquiryId}")
    public ResponseEntity<?> responseToUser(@RequestBody ContactEnquiriesRequest2 contactEnquiriesRequest, @PathVariable int enquiryId){
        return contestEnquiriesService.responseToUser(contactEnquiriesRequest, enquiryId);
    }

    @GetMapping("/getEnquiry")
    public ResponseEntity<?> getAll(){
        return contestEnquiriesService.getAll();
    }
    @GetMapping("/getEnquiry/{enquiryId}")
    public Object getDetailsById(@PathVariable int enquiryId){
        return contestEnquiriesService.getDetailsById(enquiryId);
    }

    @DeleteMapping("/enquiry/{enquiryId}")
    public ResponseEntity<?> deleteEnquiry(@PathVariable int enquiryId){
        return contestEnquiriesService.deleteById(enquiryId);
    }

    @PutMapping("/enquiry/{enquiryId}")
    public ResponseEntity<?> editEnquiry(@RequestBody ContactEnquiriesRequest contactEnquiriesRequest, @PathVariable int enquiryId) {
        return contestEnquiriesService.editDetails(enquiryId, contactEnquiriesRequest);

    }
}

package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.ContactEnquiries;
import com.b2bAr.arzoo.entity.UserEntity;
import com.b2bAr.arzoo.mapper.ContactEnquiriesMapper;
import com.b2bAr.arzoo.repository.ContactEnquiriesRepository;
import com.b2bAr.arzoo.repository.UserRepository;
import com.b2bAr.arzoo.request.ContactEnquiriesRequest;
import com.b2bAr.arzoo.request.ContactEnquiriesRequest2;
import com.b2bAr.arzoo.response.ContactEnquiriesResponse;
import com.b2bAr.arzoo.response.DealsResponse;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactEnquiriesService {



    public final ContactEnquiriesRepository contactEnquiriesRepository;

    public final ContactEnquiriesMapper contestEnquiriesMapper;

    public final UserRepository userRepository;

    @Autowired
    public ContactEnquiriesService(ContactEnquiriesRepository contactEnquiriesRepository, ContactEnquiriesMapper contestEnquiriesMapper, UserRepository userRepository) {
        this.contactEnquiriesRepository = contactEnquiriesRepository;
        this.contestEnquiriesMapper = contestEnquiriesMapper;
        this.userRepository = userRepository;
    }


    public ResponseEntity<?> post(ContactEnquiriesRequest contactEnquiriesRequest, int userId) {
        try {
            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (userEntity.isPresent()) {
                UserEntity userEntity1 = userEntity.get();
                contactEnquiriesRequest.setEnquiryDate(new java.util.Date());
                ContactEnquiries entity = contestEnquiriesMapper.requestToEntity(contactEnquiriesRequest);
                entity.setUserEntity(userEntity1);
                contactEnquiriesRepository.save(entity);
                log.info("Post Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Post Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("UserID is not exist");
                ResultResponse response = new ResultResponse();
                response.setResult("user Id is not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> responseToUser(ContactEnquiriesRequest2 contactEnquiriesRequest, int enquiryId) {
        try {
            Optional<ContactEnquiries> contactEnquiries = contactEnquiriesRepository.findById(enquiryId);
            if (contactEnquiries.isPresent()) {
                ContactEnquiries contactEnquiries1 = contactEnquiries.get();
                ContactEnquiries entity = contestEnquiriesMapper.requestToEntity2(contactEnquiries1, contactEnquiriesRequest);
                contactEnquiriesRepository.save(entity);
                log.info("Responded");
                ResultResponse response = new ResultResponse();
                response.setResult("responded");
                return ResponseEntity.ok(response);
            }else {
                log.error("There is no message");
                ResultResponse response = new ResultResponse();
                response.setResult("There is no message");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> getDetailsById(int enquiryId) {
        try {
            Optional<ContactEnquiries> entity = contactEnquiriesRepository.findById(enquiryId);
            if (entity.isPresent()) {
                ContactEnquiriesResponse contactEnquiriesResponse  = contestEnquiriesMapper.entityToResponse(entity.get());
                return ResponseEntity.ok(contactEnquiriesResponse);
            } else {
                log.error("There is no Records");
                ResultResponse response = new ResultResponse();
                response.setResult("there is no records");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> getAll() {
        List<ContactEnquiries> entity = contactEnquiriesRepository.findAll();
        if (!entity.isEmpty()) {
            List<ContactEnquiriesResponse> contactEnquiriesResponses = contestEnquiriesMapper.entityToResponse2(entity);
            return ResponseEntity.ok(contactEnquiriesResponses);
        } else {
            log.error("Empty Records ");
            ResultResponse response = new ResultResponse();
            response.setResult("Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<?> deleteById(int enquiryId) {
        Optional<ContactEnquiries> contactEnquiries = contactEnquiriesRepository.findById(enquiryId);// here we are searching the details by id
        try {

            if (contactEnquiries.isPresent()) {// if the details are present
                contactEnquiriesRepository.deleteById(enquiryId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Check id "+enquiryId);
                ResultResponse response = new ResultResponse();
                response.setResult("check id "+enquiryId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int enquiryId, ContactEnquiriesRequest contestEnquiriesRequest) {
        try {
            Optional<ContactEnquiries> contestEnquiries= contactEnquiriesRepository.findById(enquiryId);// we are Searching  the details by userId

            if (contestEnquiries.isPresent()) {
                ContactEnquiries existingDetails = contestEnquiries.get();// we are get them by using get() from userEntity
                ContactEnquiries contestEnquiries1 = contestEnquiriesMapper.updateRequest(existingDetails, contestEnquiriesRequest);// we are giving the values through the userUpdateRequestclass

                contactEnquiriesRepository.save(contestEnquiries1);// here we are saving those values
                log.info("Updated Successfully");
                ResultResponse response = new ResultResponse();// this is response class
                response.setResult("Updated successfully");// this is the response for make sure that the details are updated are not
                return ResponseEntity.ok(response);// we are returning the response
            }else {
                log.error("Update is failed, Check Id "+enquiryId);
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not Updated check the Id "+enquiryId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("details not saved");
    }
}


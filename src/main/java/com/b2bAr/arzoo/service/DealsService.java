package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.DealsEntity;
import com.b2bAr.arzoo.entity.UserEntity;
import com.b2bAr.arzoo.mapper.DealsMapper;
import com.b2bAr.arzoo.repository.DealsRepository;
import com.b2bAr.arzoo.request.DealsRequest;
import com.b2bAr.arzoo.response.DealsResponse;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DealsService {

    @Autowired
    public final DealsRepository dealsRepository;

    @Autowired
    public final DealsMapper dealsMapper;


    public DealsService(DealsRepository dealsRepository, DealsMapper dealsMapper) {
        this.dealsRepository = dealsRepository;
        this.dealsMapper = dealsMapper;
    }

    public ResponseEntity<?> saveDeals(DealsRequest dealsRequest){
        try {
            Optional<DealsEntity> entity = dealsRepository.findDealName(dealsRequest.getDealName());
            if (entity.isEmpty()) {
                Date today = new Date();
                if(dealsRequest.getEndDate().after(today)) {
                    DealsEntity dealsEntity = dealsMapper.requestToEntity(dealsRequest);
                    dealsRepository.save(dealsEntity);
                    log.info("Saved Successfully");
                    ResultResponse response = new ResultResponse();
                    response.setResult("Saved Successfully");
                    return ResponseEntity.ok(response);
                }else {
                    log.info("End date is expired");
                    ResultResponse response = new ResultResponse();
                    response.setResult("end Date expired");
                    return ResponseEntity.ok(response);
                }
            }else {
                log.error("The Deal name is already exist "+dealsRequest.getDealName());
                ResultResponse response = new ResultResponse();
                response.setResult("The deals Name is already Exist "+dealsRequest.getDealName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }

    public ResponseEntity<?> getDeals(){
        try {
            List<DealsEntity> entities = dealsRepository.findAll();
            if (!entities.isEmpty()){
                List<DealsResponse> dealsResponse = new ArrayList<>();
                for (DealsEntity entity : entities) {
                    DealsResponse dealsResponses = dealsMapper.entityToResponse2(entity);

                    Date startDate = entity.getStartDate();
                    Date endDate = entity.getEndDate();

                    if (startDate.after(endDate)) {

                        dealsRepository.deleteById(entity.getDealId());
                        log.info("Deal is end");
                        ResultResponse response = new ResultResponse();
                        response.setResult("deal is end");
                        return ResponseEntity.ok(response);
                    }
                    dealsResponse.add(dealsResponses);
                }
                return ResponseEntity.ok(dealsResponse);

            }else {
                log.error("Empty Records");
                ResultResponse response = new ResultResponse();
                response.setResult("Empty Records");
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            ResultResponse response  = new ResultResponse();
            response.setResult(e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<?> getDetailsById(int dealId) {
        try {
            Optional<DealsEntity> entity = dealsRepository.findById(dealId);
            if (entity.isPresent()) {
                DealsEntity entity1 = entity.get();
                Date startDate = entity1.getStartDate();
                Date endDate  = entity1.getEndDate();

                if (startDate.after(endDate)){
                    log.info("Deal is end");
                    ResultResponse response = new ResultResponse();
                    response.setResult("deal is end");
                    return ResponseEntity.ok(response);
                }
                DealsResponse dealsResponse = dealsMapper.entityToResponse2(entity.get());
                return ResponseEntity.ok(dealsResponse);
            } else {
                log.error("No Deals");
                ResultResponse response = new ResultResponse();
                response.setResult("No Deals");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<?> deleteById(int dealId) {
        Optional<DealsEntity> entity = dealsRepository.findById(dealId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                dealsRepository.deleteById(dealId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Check Id "+dealId);
                ResultResponse response = new ResultResponse();
                response.setResult("check Id "+dealId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    public ResponseEntity<?> editDetails(int dealId,  DealsRequest dealsRequest) {
        try {
            Optional<DealsEntity> entity = dealsRepository.findById(dealId);
            if(entity.isPresent()) {
                DealsEntity existingDetails = entity.get();
                DealsEntity updateEntity = dealsMapper.updateRequest(existingDetails, dealsRequest);

                dealsRepository.save(updateEntity);
                log.info("Updated Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Updated successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Update failed, Check Id "+dealId);
                ResultResponse response = new ResultResponse();
                response.setResult("details not updated, check the Id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

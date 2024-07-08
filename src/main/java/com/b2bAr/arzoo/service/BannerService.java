package com.b2bAr.arzoo.service;

import com.b2bAr.arzoo.entity.BannersEntity;
import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.mapper.BannerMapper;
import com.b2bAr.arzoo.repository.BannerRepository;
import com.b2bAr.arzoo.request.BannerRequest;
import com.b2bAr.arzoo.response.BannerResponse;
import com.b2bAr.arzoo.response.ProductResponse;
import com.b2bAr.arzoo.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class BannerService {

    @Autowired
    public final BannerRepository bannerRepository;

    @Autowired
    public final BannerMapper bannerMapper;

    @Value("${file.upload.path}")
    private String folderPath;


    public BannerService(BannerRepository bannerRepository, BannerMapper bannerMapper) {
        this.bannerRepository = bannerRepository;
        this.bannerMapper = bannerMapper;
    }

    public ResponseEntity<?> saveBanner(BannerRequest bannerRequest, MultipartFile file){
        try {

            BannersEntity entity = bannerMapper.requestToEntity(bannerRequest);
            if (!file.isEmpty()){
                String filename = folderPath + file.getOriginalFilename();
                file.transferTo(new File(filename));
                entity.setImage_url(filename);
            }
            bannerRepository.save(entity);
            log.info("Saved Successfully");
            ResultResponse response = new ResultResponse();
            response.setResult("Saved Successfully");
            return ResponseEntity.ok(response);


        }catch (Exception e){
            log.error(e.getMessage());
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<?> getAllBanners() {
        List<BannersEntity> allBanners = bannerRepository.findAll();
        if (!allBanners.isEmpty()) {
            List<BannerResponse> bannerResponses = new ArrayList<>();
            for (BannersEntity bannerEntity : allBanners) {
                BannerResponse bannerResponse = bannerMapper.entityToResponse(bannerEntity);
                String imageUrl = bannerEntity.getImage_url();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    try {
                        File file = new File(imageUrl);
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        byte[] fileContent2 = Files.readAllBytes(Paths.get(imageUrl));
                        String base = Base64.getEncoder().encodeToString(fileContent2);
                        bannerResponse.setImage_url(base);
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        ResultResponse response = new ResultResponse();
                        response.setResult("Error occurred while reading the image file for banner ID: " + bannerEntity.getBannerId());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                    }
                }
                bannerResponses.add(bannerResponse);
            }
            return ResponseEntity.ok(bannerResponses);
        } else {
            log.error("No records found");
            ResultResponse response = new ResultResponse();
            response.setResult("No banners found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<?> getBannerById(int bannerId) {
        Optional<BannersEntity> bannerOptional = bannerRepository.findById(bannerId);
        if (bannerOptional.isPresent()) {
            BannersEntity bannerEntity = bannerOptional.get();
            BannerResponse bannerResponse = bannerMapper.entityToResponse(bannerEntity);
            // Assuming you have a getImageUrl() method in your BannersEntity class to retrieve the image URL
            String imageUrl = bannerEntity.getImage_url();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    File file = new File(imageUrl);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    byte[] fileContent2 = Files.readAllBytes(Paths.get(imageUrl));
                    String base = Base64.getEncoder().encodeToString(fileContent2);

                    bannerResponse.setImage_url(base);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    ResultResponse response = new ResultResponse();
                    response.setResult("Error occurred while reading the image file");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            }
            return ResponseEntity.ok(bannerResponse);
        } else {
            log.error("Banner is not found");
            ResultResponse response = new ResultResponse();
            response.setResult("Banner not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    public ResponseEntity<?> deleteById(int bannerId) {
        Optional<BannersEntity> entity = bannerRepository.findById(bannerId);// here we are searching the details by id
        try {

            if (entity.isPresent()) {// if the details are present
                bannerRepository.deleteById(bannerId);
                log.info("Deleted Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Deleted Successfully");
                return ResponseEntity.ok(response);
            }else {
                log.error("Check Id "+bannerId);
                ResultResponse response = new ResultResponse();
                response.setResult("check Id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            ResultResponse response = new ResultResponse();
            response.setResult(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<?> editBanner(int bannerId, BannerRequest bannerRequest, MultipartFile newFile) throws IOException {
        try {
            Optional<BannersEntity> entity = bannerRepository.findById(bannerId);
            if (entity.isPresent()){
                BannersEntity entity1 = entity.get();
                BannersEntity updateEntity = bannerMapper.updateRequest(entity1, bannerRequest);

                if (!newFile.isEmpty()){
                    String filename = folderPath+newFile.getOriginalFilename();
                    newFile.transferTo(new File(filename));
                    updateEntity.setImage_url(filename);
                }
                bannerRepository.save(updateEntity);
                log.info("Updated Successfully");
                ResultResponse response = new ResultResponse();
                response.setResult("Updated successfully");
                return ResponseEntity.ok(response);
            } else {
                log.error("Failed to updated, check id "+bannerId);
                ResultResponse response = new ResultResponse();
                response.setResult("Details are not updated, Check your ID");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

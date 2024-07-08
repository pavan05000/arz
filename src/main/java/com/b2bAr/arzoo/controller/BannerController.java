package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.entity.BannersEntity;
import com.b2bAr.arzoo.request.BannerRequest;
import com.b2bAr.arzoo.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    public final BannerService bannerService;


    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProducts(@ModelAttribute BannerRequest bannerRequest,
                                         @RequestPart("file") MultipartFile file) {
        System.out.println("the controller upload hit...");

        return bannerService.saveBanner(bannerRequest,file);
    }
    @GetMapping("/getAllBanner")
    public ResponseEntity<?> getAllBanner(){
        return bannerService.getAllBanners();
    }
    @GetMapping("/getBanner/{bannerId}")
    public ResponseEntity<?> getDetailsById(@PathVariable int bannerId){
        return bannerService.getBannerById(bannerId);
    }
    @DeleteMapping("/delete/{bannerId}")
    public ResponseEntity<?> delete(@PathVariable int bannerId){
        return bannerService.deleteById(bannerId);
    }

   @PutMapping("/editBanner/{bannerId}")
    public ResponseEntity<?> editBanner(@PathVariable int bannerId, @ModelAttribute BannerRequest bannerRequest, @RequestPart("file") MultipartFile file) throws IOException {
        return bannerService.editBanner(bannerId, bannerRequest, file);
   }
}

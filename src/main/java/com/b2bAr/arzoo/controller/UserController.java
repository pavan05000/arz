package com.b2bAr.arzoo.controller;

import com.b2bAr.arzoo.request.UserRequest;
import com.b2bAr.arzoo.request.UserUpdateRequest;
import com.b2bAr.arzoo.response.UserResponse;
import com.b2bAr.arzoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    public UserService userService;

    UserController(UserService userService){
        this.userService= userService;
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest){
        return userService.register(userRequest);
    }
    @PostMapping("/admin/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest){
        return userService.login(userRequest);
    }

    @PostMapping("/admin/logOut/{userId}")
    public ResponseEntity<?> logOut(@PathVariable int  userId){
        return userService.logOut(userId);
    }

    @GetMapping("/getDetails/{userId}")
    public Object getDetailsById(@PathVariable int userId){
        return userService.getDetailsById(userId);
    }
    @GetMapping("/getDetails")
    public ResponseEntity<?> getAll(){
        return userService.getAll();
    }


    @GetMapping("/getCartWithUser/{userId}")
    public Object getCartWithUser(@PathVariable int userId){
        return userService.getCartWithUser(userId);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteDetailsById(@PathVariable int userId){
        return userService.deleteById(userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateDetails(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable int userId){
        return userService.editDetails(userId, userUpdateRequest);
    }
}

